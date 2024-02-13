package babfriend.api.auth;

import babfriend.api.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenProvider tokenProvider;
    private final AuthService authService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        // String refreshToken = tokenProvider.resolveRefreshToken(request);
        String accessToken = tokenProvider.resolveAccessToken(request);
        // authService.logout(accessToken);
        authService.addBlackList(accessToken);
    }
}
