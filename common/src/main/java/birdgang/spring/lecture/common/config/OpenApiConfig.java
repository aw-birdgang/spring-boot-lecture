package birdgang.spring.lecture.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot Lecture API")
                        .description("Spring Boot 강의를 위한 API 문서")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("BirdGang")
                                .email("birdgang@example.com")));
    }
}
