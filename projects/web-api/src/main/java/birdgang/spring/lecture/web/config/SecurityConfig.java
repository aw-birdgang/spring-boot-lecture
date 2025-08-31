package birdgang.spring.lecture.web.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                         @Qualifier("commonCorsConfigurationSource") CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            .authorizeHttpRequests(authz -> authz
                // Swagger UI 관련 경로 허용
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                // API 엔드포인트 허용
                .requestMatchers("/web/api/**").permitAll()
                // H2 Console 허용 (개발 환경에서만)
                .requestMatchers("/h2-console/**").permitAll()
                // Actuator 엔드포인트 허용
                .requestMatchers("/actuator/**").permitAll()
                // 개발 환경에서는 모든 요청 허용
                .anyRequest().permitAll()
            )
            .csrf(csrf -> csrf
                // 개발 환경에서는 CSRF 비활성화
                .disable()
            )
            .headers(headers -> headers
                // H2 Console을 위한 프레임 옵션 비활성화
                .frameOptions().sameOrigin()
            );

        // 개발 환경에서는 폼 로그인 비활성화
        if (!"dev".equals(activeProfile)) {
            http.formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )
            .logout(logout -> logout
                .permitAll()
            );
        }

        return http.build();
    }
}
