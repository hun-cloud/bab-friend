package babfriend.api.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor()
//                .excludePathPatterns(URI + "/swagger-resources/**", URI + "/swagger-ui/**", URI + "/v3/api-docs", URI + "/api-docs/**")
//                .excludePathPatterns("/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs", "/api-docs/**")
//                .excludePathPatterns("/signUp", "/signIn", "/error/**", "/reissue")
//                .addPathPatterns("/**");
    }


}
