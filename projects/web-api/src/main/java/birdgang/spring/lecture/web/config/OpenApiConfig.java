package birdgang.spring.lecture.web.config;

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
    
    @Bean("webOpenAPI")
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Web API - Spring Boot Lecture")
                        .description("웹 애플리케이션을 위한 API 문서")
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
                                .url("http://localhost:8082/dev")
                                .description("Web API Server (Dev)"),
                        new Server()
                                .url("http://localhost:9082/staging")
                                .description("Web API Server (Staging)"),
                        new Server()
                                .url("http://localhost:10082/prod")
                                .description("Web API Server (Prod)")
                ));
    }
}
