package birdgang.spring.lecture.database.repository;

import birdgang.spring.lecture.database.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    // 발행된 게시글만 조회
    Page<Post> findByStatus(Post.PostStatus status, Pageable pageable);
    
    // 특정 사용자의 발행된 게시글 조회
    Page<Post> findByAuthorIdAndStatus(Long authorId, Post.PostStatus status, Pageable pageable);
    
    // 카테고리별 발행된 게시글 조회
    Page<Post> findByCategoryAndStatus(String category, Post.PostStatus status, Pageable pageable);
    
    // 제목에 특정 키워드가 포함된 발행된 게시글 검색
    Page<Post> findByTitleContainingIgnoreCaseAndStatus(String title, Post.PostStatus status, Pageable pageable);
    
    // 내용에 특정 키워드가 포함된 발행된 게시글 검색
    Page<Post> findByContentContainingIgnoreCaseAndStatus(String content, Post.PostStatus status, Pageable pageable);
    
    // 특정 사용자가 작성한 게시글 조회 (상태 무관)
    Page<Post> findByAuthorId(Long authorId, Pageable pageable);
    
    // 복합 검색 (제목, 내용, 카테고리)
    @Query("SELECT p FROM Post p WHERE p.status = :status AND " +
           "(:keyword IS NULL OR " +
           "LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND (:category IS NULL OR p.category = :category) " +
           "AND (:authorId IS NULL OR p.author.id = :authorId)")
    Page<Post> searchPosts(@Param("status") Post.PostStatus status,
                          @Param("keyword") String keyword,
                          @Param("category") String category,
                          @Param("authorId") Long authorId,
                          Pageable pageable);
    
    // 인기 게시글 조회 (조회수 기준)
    @Query("SELECT p FROM Post p WHERE p.status = :status ORDER BY p.viewCount DESC, p.createdAt DESC")
    Page<Post> findPopularPosts(@Param("status") Post.PostStatus status, Pageable pageable);
    
    // 최신 게시글 조회
    @Query("SELECT p FROM Post p WHERE p.status = :status ORDER BY p.createdAt DESC")
    Page<Post> findRecentPosts(@Param("status") Post.PostStatus status, Pageable pageable);
    
    // 카테고리 목록 조회
    @Query("SELECT DISTINCT p.category FROM Post p WHERE p.status = :status AND p.category IS NOT NULL")
    List<String> findCategoriesByStatus(@Param("status") Post.PostStatus status);
    
    // 사용자별 게시글 수 조회
    @Query("SELECT COUNT(p) FROM Post p WHERE p.author.id = :authorId AND p.status = :status")
    Long countByAuthorIdAndStatus(@Param("authorId") Long authorId, @Param("status") Post.PostStatus status);
    
    // 전체 게시글 수 조회 (상태별)
    @Query("SELECT COUNT(p) FROM Post p WHERE p.status = :status")
    Long countByStatus(@Param("status") Post.PostStatus status);
}
