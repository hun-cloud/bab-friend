package babfriend.api.auth;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    public void logout(String refreshToken, String accessToken) {
        Claims claims = tokenProvider.parseClaims(refreshToken);
        String email = claims.getSubject();
        String redisRefreshToken = redisTemplate.opsForValue().get(email);

        if (refreshToken.equals(redisRefreshToken)) {
            redisTemplate.delete(email);
            log.info("refresh 삭제 완료");
        }
    }

    public void addBlackList(String accessToken) {
        redisTemplate.opsForValue()
                .set(accessToken, "logout");
    }

    public void logoutCheck(String accessToken) {
        String result = redisTemplate.opsForValue().get(accessToken);

        if (StringUtils.hasText(result) && result.equals("logout")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");
        }
    }
}
