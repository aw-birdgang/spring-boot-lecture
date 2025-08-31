package birdgang.spring.lecture.repository;

import birdgang.spring.lecture.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    // 제목에 특정 키워드가 포함된 게시글 검색
    Page<Post> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    
    // 내용에 특정 키워드가 포함된 게시글 검색
    Page<Post> findByContentContainingIgnoreCase(String content, Pageable pageable);
    
    // 특정 사용자가 작성한 게시글 조회
    Page<Post> findByAuthorId(Long authorId, Pageable pageable);
    
    // 제목 또는 내용에 특정 키워드가 포함된 게시글 검색
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
    Page<Post> findByTitleOrContentContaining(String keyword, Pageable pageable);
}
