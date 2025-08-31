package birdgang.spring.lecture.web.service.impl;

import birdgang.spring.lecture.shared.dto.UserDto;
import birdgang.spring.lecture.shared.util.UserMapper;
import birdgang.spring.lecture.database.entity.User;
import birdgang.spring.lecture.database.repository.UserRepository;
import birdgang.spring.lecture.web.service.WebUserService;
import birdgang.spring.lecture.common.exception.DuplicateResourceException;
import birdgang.spring.lecture.common.exception.ResourceNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WebUserServiceImpl implements WebUserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public WebUserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public UserDto.Response createUser(UserDto.CreateRequest request) {
        // 중복 검사
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("User", "username", request.getUsername());
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("User", "email", request.getEmail());
        }
        
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        
        // 웹 사용자 생성
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
    @Cacheable(value = "webUser", key = "#id")
    public UserDto.Response getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return UserMapper.toResponse(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "webUserByUsername", key = "#username")
    public UserDto.Response getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        return UserMapper.toResponse(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "webAllUsers")
    public List<UserDto.Response> getAllUsers() {
        return userRepository.findAll().stream()
            .map(UserMapper::toResponse)
            .collect(Collectors.toList());
    }
    
    @Override
    @CacheEvict(value = {"webUser", "webUserByUsername", "webAllUsers"}, allEntries = true)
    public UserDto.Response updateUser(Long id, UserDto.UpdateRequest request) {
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
    @CacheEvict(value = {"webUser", "webUserByUsername", "webAllUsers"}, allEntries = true)
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        userRepository.deleteById(id);
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
