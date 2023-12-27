package babfriend.api.auth.service;

import babfriend.api.auth.TokenProvider;
import babfriend.api.auth.dto.KakaoLoginResponseDto;
import babfriend.api.auth.dto.KakaoUserInfoDto;
import babfriend.api.auth.dto.TokenDto;
import babfriend.api.common.exception.ErrorCode;
import babfriend.api.common.exception.RedisValueNotFoundException;
import babfriend.api.common.exception.TokenIsNotSameException;
import babfriend.api.redis.RedisService;
import babfriend.api.user.dto.UserDto;
import babfriend.api.user.entity.User;
import babfriend.api.user.repository.UserRepository;
import babfriend.api.user.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final RedisService redisService;
    private final UserRepository userRepository;

    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_REST_API_KEY = "049e57913a91b56754510b7734524584";
    private static final String REDIRECT_URL = "http://localhost:8080/login/oauth2/code/kakao";
    private static final String KAKAO_INFO_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String KAKAO_CLIENT_SECRET = "NdNDvPHiPsynS1u47au7SMo5I785ewtm";

    public void logout(String refreshToken, String accessToken) {
        Claims claims = tokenProvider.parseClaims(refreshToken);
        String email = claims.getSubject();

        String redisRefreshToken = redisService.getValue(email).orElseThrow(
                () -> new EntityNotFoundException(String.format("레디스에서 %s value를 찾을 수 없습니다.", email)));

        if (refreshToken.equals(redisRefreshToken)) {
            redisService.delete(email);
            log.info("refresh 삭제 완료");
        }
    }

    public void addBlackList(String accessToken) {
        redisService.setValue(accessToken, "logout");
    }

    public boolean logoutCheck(String accessToken) {
        String result = redisService.getValue(accessToken).orElse("");

        if (StringUtils.hasText(result) && result.equals("logout")) {
            return false;
        }
        return true;
    }

    public KakaoLoginResponseDto getKakaoToken(String code) {
        try {
            log.info("code" + code);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

            // 2. body 생성
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "authorization_code");
            body.add("client_id", KAKAO_REST_API_KEY);
            body.add("client_secret", KAKAO_CLIENT_SECRET);
            body.add("redirect_uri", REDIRECT_URL);
            body.add("code", code);

            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.postForObject(
                    KAKAO_TOKEN_URL,
                    new HttpEntity<>(body, httpHeaders),
                    KakaoLoginResponseDto.class
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public UserDto getUserInfo(KakaoLoginResponseDto kakaoLoginResponseDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoLoginResponseDto.getAccess_token());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> kakaoUserInfo = restTemplate.postForObject(
                KAKAO_INFO_URL,
                new HttpEntity<>(headers),
                Map.class
        );

        return UserDto.of(kakaoUserInfo);
    }

    public TokenDto createToken(UserDto userDto) {
        String accessToken = tokenProvider.createAccessToken(userDto);
        String refreshToken = tokenProvider.createRefreshToken(userDto);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String reissueAccessToken(TokenDto tokenDto) {
        Claims claims = tokenProvider.parseClaims(tokenDto.getRefreshToken());
        String email = claims.getSubject();

        String redisRefreshToken = redisService.getValue(email)
                .orElseThrow(() -> new RedisValueNotFoundException());

        if (!redisRefreshToken.equals(tokenDto.getRefreshToken())) {
            throw new TokenIsNotSameException();
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException());

        UserDto userDto = UserDto.of(user);

        return tokenProvider.createAccessToken(userDto);
    }
}
