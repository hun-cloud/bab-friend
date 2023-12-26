package babfriend.api.auth;

import babfriend.api.user.dto.UserDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider implements InitializingBean { // 추가 라이브러리르 사용해서 JWT를 생성하고 검증하는 컴포넌트

    private final RedisTemplate<String, String> redisTemplate;

    private static final Long accessTokenValidTime = Duration.ofMinutes(30).toMillis();
    private static final Long refreshTokenValidTime = Duration.ofDays(7).toMillis();

    private static final String BEARER_TYPE = "Bearer";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String REFRESH_HEADER = "Refresh";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private Key key;

    public TokenProvider(RedisTemplate<String, String> redisTemplate, @Value("${jwt.secret}") String secret) {
        this.redisTemplate = redisTemplate;
        this.secret = secret;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 액세스 토큰 생성
    public String createAccessToken(Authentication authentication) {
        String accessToken = createToken(authentication, "access", accessTokenValidTime);
        log.info("accessToken");
        log.info(accessToken);
        return accessToken;
    }

    // 리프레쉬 토큰 생성
    public String createRefreshToken(Authentication authentication) {
        String refreshToken = createToken(authentication, "refresh", refreshTokenValidTime);

        // redis에 저장
        redisTemplate.opsForValue().set(
                authentication.getName(),
                refreshToken,
                refreshTokenValidTime,
                TimeUnit.MILLISECONDS
        );

        log.info("refreshToken");
        log.info(refreshToken);

        return refreshToken;
    }

    // 토큰 생성
    private String createToken(Authentication authentication, String type, Long tokenValidTime) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities) // 정보 저장
                .signWith(key, SignatureAlgorithm.HS512) // 사용할 알고리즘, signature에 들어갈 secret 세팅
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime)) // 만료시간 설정
                .compact();
    }

    private String createToken(UserDto userDto, String type, Long tokenValidTime) {
        String authorities = "ROLE_USER";  // Assuming a default role for simplicity

        return Jwts.builder()
                .setSubject(userDto.getEmail())  // Assuming email as the unique identifier
                .claim(AUTHORITIES_KEY, authorities)  // Information storage
                .signWith(key, SignatureAlgorithm.HS512)  // Algorithm and secret for the signature
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime))
                .compact();
    }

    // 토큰으로 클레임 만들고, 유저 객체를 만들어서 authentication 객체를 리턴
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // token 복호화
    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰의 유효성 검증을 수행
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {

            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {

            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {

            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {

            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(REFRESH_HEADER);
        if (StringUtils.hasText(bearerToken)) {
            return bearerToken;
        }
        return null;
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String createAccessToken(UserDto userDto) {
        return createToken(userDto, "access", accessTokenValidTime);
    }

    public String createRefreshToken(UserDto userDto) {
        String refreshToken = createToken(userDto, "refresh", refreshTokenValidTime);

        // redis에 저장
        redisTemplate.opsForValue().set(
                userDto.getEmail(),  // Assuming email is a unique identifier
                refreshToken,
                refreshTokenValidTime,
                TimeUnit.MILLISECONDS
        );

        return refreshToken;
    }
}
