package babfriend.api.auth;

import babfriend.api.user.dto.UserDto;
import babfriend.api.user.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final UserService userService;
    private static final String redirectUrl = "http://localhost:3000/";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 이메일
        String email = oAuth2User.getAttribute("email");
        // 플랫폼
        String provider = oAuth2User.getAttribute("provider");
        // 회원 가입 여부 : true : 회원 , false : 비회원
        boolean isExist = oAuth2User.getAttribute("exist");

        String role = oAuth2User.getAuthorities().stream()
                .findFirst()
                .orElseThrow(IllegalAccessError::new)
                .getAuthority();

        oAuth2User.getAttributes().forEach((k, v) -> System.out.println("k : " + k + " v :" + v));

        // 회원이 아닐 시 회원가입
        if (!isExist) {
            UserDto userDto = UserDto.of(oAuth2User);
            userService.join(userDto);
        }

        // 회원 존재
        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        response.addHeader("Authorization", "Bearer " + accessToken);
        // response.addCookie(createCookie("Refresh", refreshToken));

        response.sendRedirect(redirectUrl);
    }

    private Cookie createCookie(String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setHttpOnly(true);
        // cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);
        return cookie;
    }
}
