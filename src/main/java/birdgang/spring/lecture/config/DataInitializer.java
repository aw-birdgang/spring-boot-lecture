package birdgang.spring.lecture.config;

import birdgang.spring.lecture.entity.Post;
import birdgang.spring.lecture.entity.User;
import birdgang.spring.lecture.repository.PostRepository;
import birdgang.spring.lecture.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    
    @Autowired
    public DataInitializer(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // 샘플 사용자 생성
        if (userRepository.count() == 0) {
            User user1 = new User("john_doe", "john@example.com", "password123", "John Doe");
            User user2 = new User("jane_smith", "jane@example.com", "password456", "Jane Smith");
            User user3 = new User("admin", "admin@example.com", "admin123", "Administrator");
            
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            
            // 샘플 게시글 생성
            Post post1 = new Post(
                "Spring Boot 시작하기",
                "Spring Boot는 Spring Framework를 기반으로 한 애플리케이션 개발을 간소화한 프레임워크입니다. " +
                "자동 설정, 내장 서버, 스타터 의존성 등을 제공하여 개발자가 비즈니스 로직에 집중할 수 있도록 도와줍니다.",
                user1
            );
            
            Post post2 = new Post(
                "JPA와 Hibernate 이해하기",
                "JPA(Java Persistence API)는 Java 객체를 데이터베이스에 저장하고 조회하는 방법을 정의한 API입니다. " +
                "Hibernate는 JPA의 구현체로, 객체 관계 매핑(ORM)을 제공합니다.",
                user2
            );
            
            Post post3 = new Post(
                "RESTful API 설계 원칙",
                "RESTful API는 웹의 기본 원칙을 따르는 API 설계 방식입니다. " +
                "HTTP 메서드를 적절히 사용하고, 리소스 중심의 URL 구조를 가지며, 상태가 없는 통신을 지향합니다.",
                user1
            );
            
            Post post4 = new Post(
                "Spring Security 기초",
                "Spring Security는 Spring 기반 애플리케이션의 보안을 담당하는 프레임워크입니다. " +
                "인증, 권한 부여, 세션 관리 등의 보안 기능을 제공합니다.",
                user3
            );
            
            postRepository.save(post1);
            postRepository.save(post2);
            postRepository.save(post3);
            postRepository.save(post4);
            
            System.out.println("샘플 데이터가 성공적으로 초기화되었습니다.");
        }
    }
}
