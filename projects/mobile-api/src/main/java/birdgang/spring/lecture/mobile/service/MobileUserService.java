package birdgang.spring.lecture.mobile.service;

import birdgang.spring.lecture.shared.dto.UserDto;

public interface MobileUserService {
    
    // 모바일 사용자 등록
    UserDto.Response registerUser(UserDto.CreateRequest request);
    
    // 모바일 사용자 프로필 조회
    UserDto.Response getUserProfile(Long id);
    
    // 모바일 사용자 프로필 수정
    UserDto.Response updateProfile(Long id, UserDto.UpdateRequest request);
    
    // 사용자명 중복 확인
    boolean existsByUsername(String username);
    
    // 이메일 중복 확인
    boolean existsByEmail(String email);
}
