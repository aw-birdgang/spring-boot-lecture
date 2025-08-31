package birdgang.spring.lecture.mobile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                // Swagger UI 관련 경로 허용
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                // API 엔드포인트 허용 (실제 운영에서는 인증 필요)
                .requestMatchers("/mobile/api/**").permitAll()
                // H2 Console 허용 (개발 환경에서만)
                .requestMatchers("/h2-console/**").permitAll()
                // Actuator 엔드포인트 허용
                .requestMatchers("/actuator/**").permitAll()
                // 기타 모든 요청은 인증 필요
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf
                // H2 Console을 위한 CSRF 비활성화
                .ignoringRequestMatchers("/h2-console/**")
            )
            .headers(headers -> headers
                // H2 Console을 위한 프레임 옵션 비활성화
                .frameOptions().sameOrigin()
            );

        return http.build();
    }
}
