package birdgang.spring.lecture.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot Lecture API")
                        .description("Spring Boot 강의용 API 문서")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("BirdGang")
                                .email("contact@birdgang.com")
                                .url("https://github.com/birdgang"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8081")
                                .description("Mobile API Server"),
                        new Server()
                                .url("http://localhost:8082")
                                .description("Web API Server"),
                        new Server()
                                .url("http://localhost:8083")
                                .description("Admin API Server")
                ));
    }
}
