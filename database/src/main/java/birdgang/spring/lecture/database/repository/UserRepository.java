package birdgang.spring.lecture.database.repository;

import birdgang.spring.lecture.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // 사용자명으로 사용자 조회
    Optional<User> findByUsername(String username);
    
    // 이메일로 사용자 조회
    Optional<User> findByEmail(String email);
    
    // 사용자명 존재 여부 확인
    boolean existsByUsername(String username);
    
    // 이메일 존재 여부 확인
    boolean existsByEmail(String email);
}
