package birdgang.spring.lecture.mobile.service.impl;

import birdgang.spring.lecture.shared.dto.UserDto;
import birdgang.spring.lecture.shared.util.UserMapper;
import birdgang.spring.lecture.database.entity.User;
import birdgang.spring.lecture.database.repository.UserRepository;
import birdgang.spring.lecture.mobile.service.MobileUserService;
import birdgang.spring.lecture.common.exception.DuplicateResourceException;
import birdgang.spring.lecture.common.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MobileUserServiceImpl implements MobileUserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public MobileUserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public UserDto.Response registerUser(UserDto.CreateRequest request) {
        // 중복 검사
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("User", "username", request.getUsername());
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("User", "email", request.getEmail());
        }
        
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        
        // 모바일 사용자 생성
        User user = new User(
            request.getUsername(),
            request.getEmail(),
            encodedPassword,
            request.getFullName()
        );
        
        User savedUser = userRepository.save(user);
        return UserMapper.toResponse(savedUser);
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "userProfile", key = "#id")
    public UserDto.Response getUserProfile(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return UserMapper.toResponse(user);
    }
    
    @Override
    @CacheEvict(value = "userProfile", key = "#id")
    public UserDto.Response updateProfile(Long id, UserDto.UpdateRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        // 이메일 중복 검사 (다른 사용자와 중복되지 않도록)
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateResourceException("User", "email", request.getEmail());
            }
        }
        
        // 엔티티 업데이트
        UserMapper.updateEntityFromRequest(user, request);
        
        User updatedUser = userRepository.save(user);
        return UserMapper.toResponse(updatedUser);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
