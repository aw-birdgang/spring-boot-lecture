package birdgang.spring.lecture.shared.util;

import birdgang.spring.lecture.database.entity.User;
import birdgang.spring.lecture.shared.dto.UserDto;

public class UserMapper {
    
    /**
     * User 엔티티를 Response DTO로 변환
     */
    public static UserDto.Response toResponse(User user) {
        if (user == null) {
            return null;
        }
        
        UserDto.Response response = new UserDto.Response();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setActive(user.isActive());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
    
    /**
     * CreateRequest DTO를 User 엔티티로 변환 (비밀번호는 암호화되지 않은 상태)
     */
    public static User toEntity(UserDto.CreateRequest request) {
        if (request == null) {
            return null;
        }
        
        return new User(
            request.getUsername(),
            request.getEmail(),
            request.getPassword(), // 실제 사용 시에는 암호화 필요
            request.getFullName()
        );
    }
    
    /**
     * UpdateRequest DTO의 내용으로 User 엔티티 업데이트
     */
    public static void updateEntityFromRequest(User user, UserDto.UpdateRequest request) {
        if (user == null || request == null) {
            return;
        }
        
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
    }
}
