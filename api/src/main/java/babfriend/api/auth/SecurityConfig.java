package babfriend.api.auth;

import babfriend.api.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final AuthService authService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final OAuth2UserService oAuth2UserService;
    private final MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    private final CustomLogoutHandler logoutHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(configurer -> {
                    configurer.authenticationEntryPoint(jwtAuthenticationEntryPoint);
                    configurer.accessDeniedHandler(jwtAccessDeniedHandler);
                })
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(authorizeHttpRequests ->
                        {
                            authorizeHttpRequests.requestMatchers(
                                            "/login/**",
                                            "/swagger-resources/**",
                                            "/swagger-ui/**",
                                            "/v3/api-docs/**",
                                            "/api-docs/**",
                                            "/oauth2/**",
                                            "/auth/**",
                                            "/favicon.ico",
                                            "/doc.html",
                                            "/error",
                                            "swagger-ui.html").permitAll()
                                    .anyRequest().authenticated();
                        }
                )
                .apply(new JwtSecurityConfig(tokenProvider, authService));

//        httpSecurity.oauth2Login(oauth2Configurer -> oauth2Configurer
//                .loginPage("/login")
//                .successHandler(myAuthenticationSuccessHandler)
//                .userInfoEndpoint()
//                .userService(oAuth2UserService))
//                .logout(logout ->
//                        logout.logoutUrl("/logout")
//                                .addLogoutHandler(logoutHandler)
//                                .logoutSuccessHandler((request, response, authentication) -> {
//                                            SecurityContextHolder.clearContext();
//                                            response.sendRedirect("http://localhost:3000");
//                                        }));

        return httpSecurity.build();
    }
}

//                        .excludePathPatterns("/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs", "/api-docs/**")
//                .excludePathPatterns("/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs", "/api-docs/**")
//                .excludePathPatterns("/signUp", "/signIn", "/error/**", "/reissue")
//                .addPathPatterns("/**");
