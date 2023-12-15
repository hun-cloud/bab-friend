package babfriend.api.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // super.onAuthenticationSuccess(request, response, authentication);
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 이메일
        String email = oAuth2User.getAttribute("email");
        // 플랫폼
        String provider = oAuth2User.getAttribute("provider");

        boolean isExist = oAuth2User.getAttribute("exist");

        String role = oAuth2User.getAuthorities().stream()
                .findFirst()
                .orElseThrow(IllegalAccessError::new)
                .getAuthority();

        oAuth2User.getAttributes().forEach((k, v) -> System.out.println("k : " + k + " v :" + v));

        // 회원 존재
        if (isExist) {
            String accessToken = tokenProvider.createAccessToken(authentication);
            String refreshToken = tokenProvider.createRefreshToken(authentication);

            String redirectUrl = (String) request.getSession().getAttribute("prevPage");

//            UriComponentsBuilder.fromUriString("/index")
//            getRedirectStrategy().sendRedirect();

        }



    }
}
