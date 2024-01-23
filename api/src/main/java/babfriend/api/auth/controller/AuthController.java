package babfriend.api.auth.controller;

import babfriend.api.auth.dto.KakaoLoginResponseDto;
import babfriend.api.auth.dto.TokenDto;
import babfriend.api.auth.dto.TokenRefreshDto;
import babfriend.api.auth.dto.TokenResponseDto;
import babfriend.api.auth.service.AuthService;
import babfriend.api.common.ResponseDto;
import babfriend.api.user.dto.UserDto;
import babfriend.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

@Tag(name = "로그인 API", description = "로그인 관련 API")
@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private static final String REFRESH_TOKEN_STR = "Refresh";

    @Operation(summary = "토큰 발급 API")
    @GetMapping("/kakao/callback")
    public ResponseDto<TokenResponseDto> kakaoCallback(@RequestParam("code") String code, HttpServletResponse response) {
        KakaoLoginResponseDto kakaoLoginResponseDto = authService.getKakaoToken(code);

        UserDto userDto = null;
        try {
            userDto = authService.getUserInfo(kakaoLoginResponseDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        userService.join(userDto);

        TokenDto tokenDto = authService.createToken(userDto);

        ResponseCookie refreshToken = createCookie(REFRESH_TOKEN_STR, tokenDto.getRefreshToken());

        response.addHeader(HttpHeaders.SET_COOKIE, refreshToken.toString());

        return ResponseDto.success(new TokenResponseDto(tokenDto.getAccessToken()));
    }

    @Operation(summary = "액세스 토큰 재발급 API")
    @PostMapping("/auth/refresh")
    public ResponseDto<TokenResponseDto> refresh(HttpServletRequest request, HttpServletResponse response) {

        String refreshTokenInCookie = getRefreshToken(request);
        TokenDto tokenDto = TokenDto.builder()
                .refreshToken(refreshTokenInCookie)
                .build();

        TokenDto result = authService.reissueAccessToken(tokenDto);

        ResponseCookie refreshToken = createCookie(REFRESH_TOKEN_STR, tokenDto.getRefreshToken());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshToken.toString());
        return ResponseDto.success(new TokenResponseDto(result.getAccessToken()));
    }

    @Operation(summary = "쿠키 테스트 API")
    @GetMapping("/auth/cookieTest")
    public ResponseDto testCookie(HttpServletRequest request, HttpServletResponse response) {

        ResponseCookie cookie = createCookie("test", "test");
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseDto.success();
    }

    private String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            throw new RuntimeException("리프레시 토큰이 존재하지 않습니다. null 입니다.");
        }

        for(Cookie cookie : cookies) {

            if (REFRESH_TOKEN_STR.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        throw new RuntimeException("리프레시 토큰이 존재하지 않습니다.");
    }

    private ResponseCookie createCookie(String cookieName, String cookieValue) {
        ResponseCookie cookie = ResponseCookie.from(cookieName, cookieValue)
                .maxAge(60 * 60 * 24 * 7)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();
        return cookie;
    }
}
