package babfriend.api.auth;

import babfriend.api.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;
    private final AuthService authService;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(
                new JwtFilter(tokenProvider, authService),
                UsernamePasswordAuthenticationFilter.class
        );
    }
}