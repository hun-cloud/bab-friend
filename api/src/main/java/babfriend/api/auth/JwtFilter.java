package babfriend.api.auth;

import babfriend.api.auth.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;
    private final AuthService authService;

    /*
     * 필터링 로직
     * 토큰의 인증 정보를 SecurityContext에 저장하는 역할 수행
     * 검증이 끝난 JWT에 유저 정보를 받아와서 UsernamePasswordAuthenticationFilter로 전달
     */
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwt = tokenProvider.resolveAccessToken(httpServletRequest);

        String requestURI = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            authService.logoutCheck(jwt);
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Security Context에 '{}' 인증 정보를 저장했습니다, uri : {} ", authentication.getName(), requestURI);
        } else {
            log.info("유효한 JWT 토큰이 없습니다. uri : {}", requestURI);
        }

        chain.doFilter(request, response);
    }
}
