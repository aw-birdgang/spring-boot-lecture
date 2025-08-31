package birdgang.spring.lecture.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
    "birdgang.spring.lecture.common",
    "birdgang.spring.lecture.database",
    "birdgang.spring.lecture.shared",
    "birdgang.spring.lecture.admin"
})
@EntityScan(basePackages = {
    "birdgang.spring.lecture.database.entity"
})
@EnableJpaRepositories(basePackages = {
    "birdgang.spring.lecture.database.repository"
})
public class AdminApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApiApplication.class, args);
    }
}
