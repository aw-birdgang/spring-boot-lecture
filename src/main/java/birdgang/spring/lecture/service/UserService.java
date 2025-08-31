package birdgang.spring.lecture.service;

import birdgang.spring.lecture.dto.UserDto;
import java.util.List;

public interface UserService {
    
    // 사용자 생성
    UserDto.Response createUser(UserDto.CreateRequest request);
    
    // 사용자 조회 (ID로)
    UserDto.Response getUserById(Long id);
    
    // 사용자 조회 (사용자명으로)
    UserDto.Response getUserByUsername(String username);
    
    // 모든 사용자 조회
    List<UserDto.Response> getAllUsers();
    
    // 사용자 정보 수정
    UserDto.Response updateUser(Long id, UserDto.UpdateRequest request);
    
    // 사용자 삭제
    void deleteUser(Long id);
    
    // 사용자 존재 여부 확인
    boolean existsByUsername(String username);
    
    // 사용자 존재 여부 확인 (이메일)
    boolean existsByEmail(String email);
}
