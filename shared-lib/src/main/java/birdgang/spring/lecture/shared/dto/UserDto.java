package birdgang.spring.lecture.shared.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class UserDto {
    
    // 사용자 생성 요청 DTO
    public static class CreateRequest {
        @NotBlank(message = "사용자명은 필수입니다")
        @Size(min = 2, max = 50, message = "사용자명은 2자 이상 50자 이하여야 합니다")
        private String username;
        
        @NotBlank(message = "이메일은 필수입니다")
        @Email(message = "올바른 이메일 형식이 아닙니다")
        private String email;
        
        @NotBlank(message = "비밀번호는 필수입니다")
        @Size(min = 6, message = "비밀번호는 6자 이상이어야 합니다")
        private String password;
        
        private String fullName;
        
        // Getter와 Setter
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
    }
    
    // 사용자 수정 요청 DTO
    public static class UpdateRequest {
        private String fullName;
        private String email;
        
        // Getter와 Setter
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
    
    // 사용자 응답 DTO
    public static class Response {
        private Long id;
        private String username;
        private String email;
        private String fullName;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        
        // Getter와 Setter
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        
        public LocalDateTime getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    }
}
