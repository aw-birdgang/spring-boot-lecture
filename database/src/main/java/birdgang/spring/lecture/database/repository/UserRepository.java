package birdgang.spring.lecture.database.repository;

import birdgang.spring.lecture.database.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    
    // 활성화된 사용자만 조회
    Page<User> findByActiveTrue(Pageable pageable);
    
    // 비활성화된 사용자만 조회
    Page<User> findByActiveFalse(Pageable pageable);
    
    // 사용자명으로 활성화된 사용자 조회
    Optional<User> findByUsernameAndActiveTrue(String username);
    
    // 이메일로 활성화된 사용자 조회
    Optional<User> findByEmailAndActiveTrue(String email);
    
    // 검색 기능 (사용자명, 이메일, 이름으로 검색)
    @Query("SELECT u FROM User u WHERE " +
           "(:keyword IS NULL OR " +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND (:activeOnly IS NULL OR u.active = :activeOnly)")
    Page<User> searchUsers(@Param("keyword") String keyword, 
                          @Param("activeOnly") Boolean activeOnly, 
                          Pageable pageable);
}
