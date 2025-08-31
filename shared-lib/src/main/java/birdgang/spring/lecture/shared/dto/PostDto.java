package birdgang.spring.lecture.shared.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class PostDto {
    
    // 게시글 생성 요청 DTO
    public static class CreateRequest {
        @NotBlank(message = "제목은 필수입니다")
        @Size(min = 1, max = 200, message = "제목은 1자 이상 200자 이하여야 합니다")
        private String title;
        
        @NotBlank(message = "내용은 필수입니다")
        @Size(min = 1, max = 5000, message = "내용은 1자 이상 5000자 이하여야 합니다")
        private String content;
        
        @Size(max = 50, message = "카테고리는 50자 이하여야 합니다")
        private String category;
        
        // Getter와 Setter
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
    }
    
    // 게시글 수정 요청 DTO
    public static class UpdateRequest {
        @Size(min = 1, max = 200, message = "제목은 1자 이상 200자 이하여야 합니다")
        private String title;
        
        @Size(min = 1, max = 5000, message = "내용은 1자 이상 5000자 이하여야 합니다")
        private String content;
        
        @Size(max = 50, message = "카테고리는 50자 이하여야 합니다")
        private String category;
        
        // Getter와 Setter
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
    }
    
    // 게시글 응답 DTO
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private UserDto.Response author;
        private String status;
        private String category;
        private Integer viewCount;
        private Integer likeCount;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private LocalDateTime publishedAt;
        
        // Getter와 Setter
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        
        public UserDto.Response getAuthor() { return author; }
        public void setAuthor(UserDto.Response author) { this.author = author; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        
        public Integer getViewCount() { return viewCount; }
        public void setViewCount(Integer viewCount) { this.viewCount = viewCount; }
        
        public Integer getLikeCount() { return likeCount; }
        public void setLikeCount(Integer likeCount) { this.likeCount = likeCount; }
        
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        
        public LocalDateTime getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
        
        public LocalDateTime getPublishedAt() { return publishedAt; }
        public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }
    }
    
    // 게시글 목록 조회 응답 DTO
    public static class ListResponse {
        private Long id;
        private String title;
        private String content;
        private String authorUsername;
        private String status;
        private String category;
        private Integer viewCount;
        private Integer likeCount;
        private LocalDateTime createdAt;
        private LocalDateTime publishedAt;
        
        // Getter와 Setter
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        
        public String getAuthorUsername() { return authorUsername; }
        public void setAuthorUsername(String authorUsername) { this.authorUsername = authorUsername; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        
        public Integer getViewCount() { return viewCount; }
        public void setViewCount(Integer viewCount) { this.viewCount = viewCount; }
        
        public Integer getLikeCount() { return likeCount; }
        public void setLikeCount(Integer likeCount) { this.likeCount = likeCount; }
        
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        
        public LocalDateTime getPublishedAt() { return publishedAt; }
        public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }
    }
    
    // 게시글 검색 요청 DTO
    public static class SearchRequest {
        private String keyword;
        private String category;
        private Long authorId;
        private String status = "PUBLISHED";
        private String sortBy = "createdAt";
        private String sortDirection = "DESC";
        
        // Getter와 Setter
        public String getKeyword() { return keyword; }
        public void setKeyword(String keyword) { this.keyword = keyword; }
        
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        
        public Long getAuthorId() { return authorId; }
        public void setAuthorId(Long authorId) { this.authorId = authorId; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public String getSortBy() { return sortBy; }
        public void setSortBy(String sortBy) { this.sortBy = sortBy; }
        
        public String getSortDirection() { return sortDirection; }
        public void setSortDirection(String sortDirection) { this.sortDirection = sortDirection; }
    }
}
