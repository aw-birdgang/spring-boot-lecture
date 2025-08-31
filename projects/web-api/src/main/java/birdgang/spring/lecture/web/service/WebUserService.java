package birdgang.spring.lecture.web.service;

import birdgang.spring.lecture.shared.dto.UserDto;
import java.util.List;

public interface WebUserService {
    
    // 웹 사용자 생성
    UserDto.Response createUser(UserDto.CreateRequest request);
    
    // ID로 웹 사용자 조회
    UserDto.Response getUserById(Long id);
    
    // 사용자명으로 웹 사용자 조회
    UserDto.Response getUserByUsername(String username);
    
    // 모든 웹 사용자 조회
    List<UserDto.Response> getAllUsers();
    
    // 웹 사용자 정보 수정
    UserDto.Response updateUser(Long id, UserDto.UpdateRequest request);
    
    // 웹 사용자 삭제
    void deleteUser(Long id);
    
    // 사용자명 중복 확인
    boolean existsByUsername(String username);
    
    // 이메일 중복 확인
    boolean existsByEmail(String email);
}
