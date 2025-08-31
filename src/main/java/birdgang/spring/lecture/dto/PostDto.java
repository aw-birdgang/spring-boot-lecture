package birdgang.spring.lecture.dto;

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
        
        // Getter와 Setter
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }
    
    // 게시글 수정 요청 DTO
    public static class UpdateRequest {
        @Size(min = 1, max = 200, message = "제목은 1자 이상 200자 이하여야 합니다")
        private String title;
        
        @Size(min = 1, max = 5000, message = "내용은 1자 이상 5000자 이하여야 합니다")
        private String content;
        
        // Getter와 Setter
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }
    
    // 게시글 응답 DTO
    public static class Response {
        private Long id;
        private String title;
        private String content;
        private UserDto.Response author;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        
        // Getter와 Setter
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        
        public UserDto.Response getAuthor() { return author; }
        public void setAuthor(UserDto.Response author) { this.author = author; }
        
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        
        public LocalDateTime getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    }
    
    // 게시글 목록 조회 응답 DTO
    public static class ListResponse {
        private Long id;
        private String title;
        private String content;
        private String authorUsername;
        private LocalDateTime createdAt;
        
        // Getter와 Setter
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        
        public String getAuthorUsername() { return authorUsername; }
        public void setAuthorUsername(String authorUsername) { this.authorUsername = authorUsername; }
        
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    }
}
