package birdgang.spring.lecture.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {
    
    @Value("${spring.profiles.active:dev}")
    private String activeProfile;
    
    @Value("${cors.allowed-origins:}")
    private String allowedOrigins;
    
    @Bean("commonCorsConfigurationSource")
    public CorsConfigurationSource commonCorsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // 환경별 CORS 설정
        if ("prod".equals(activeProfile)) {
            // 운영 환경: 특정 도메인만 허용
            if (allowedOrigins != null && !allowedOrigins.isEmpty()) {
                configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
            } else {
                // 기본 운영 환경 도메인
                configuration.setAllowedOrigins(Arrays.asList(
                    "https://yourdomain.com",
                    "https://www.yourdomain.com"
                ));
            }
        } else {
            // 개발/테스트 환경: 모든 도메인 허용
            configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        }
        
        // 공통 설정
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L); // 1시간
        
        // 노출할 헤더 설정
        configuration.setExposedHeaders(Arrays.asList(
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Credentials"
        ));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
