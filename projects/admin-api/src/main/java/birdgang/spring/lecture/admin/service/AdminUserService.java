package birdgang.spring.lecture.admin.service;

import birdgang.spring.lecture.shared.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminUserService {
    
    // 페이징을 통한 사용자 목록 조회
    Page<UserDto.Response> getAllUsers(Pageable pageable);
    
    // ID로 사용자 조회
    UserDto.Response getUserById(Long id);
    
    // 사용자 정보 수정 (관리자 권한)
    UserDto.Response updateUser(Long id, UserDto.UpdateRequest request);
    
    // 사용자 삭제 (관리자 권한)
    void deleteUser(Long id);
    
    // 키워드로 사용자 검색
    Page<UserDto.Response> searchUsers(String keyword, Pageable pageable);
    
    // 사용자 활성화
    UserDto.Response activateUser(Long id);
    
    // 사용자 비활성화
    UserDto.Response deactivateUser(Long id);
}
