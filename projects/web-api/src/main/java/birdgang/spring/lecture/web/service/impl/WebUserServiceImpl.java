package birdgang.spring.lecture.web.service.impl;

import birdgang.spring.lecture.shared.dto.UserDto;
import birdgang.spring.lecture.database.entity.User;
import birdgang.spring.lecture.database.repository.UserRepository;
import birdgang.spring.lecture.web.service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WebUserServiceImpl implements WebUserService {
    
    private final UserRepository userRepository;
    
    @Autowired
    public WebUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDto.Response createUser(UserDto.CreateRequest request) {
        // 중복 검사
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다: " + request.getUsername());
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다: " + request.getEmail());
        }
        
        // 웹 사용자 생성 (추가 검증 로직 포함)
        User user = new User(
            request.getUsername(),
            request.getEmail(),
            request.getPassword(), // 실제로는 비밀번호 암호화 필요
            request.getFullName()
        );
        
        User savedUser = userRepository.save(user);
        return convertToResponse(savedUser);
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "webUser", key = "#id")
    public UserDto.Response getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + id));
        return convertToResponse(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "webUserByUsername", key = "#username")
    public UserDto.Response getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + username));
        return convertToResponse(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "webAllUsers")
    public List<UserDto.Response> getAllUsers() {
        return userRepository.findAll().stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }
    
    @Override
    @CacheEvict(value = {"webUser", "webUserByUsername", "webAllUsers"}, allEntries = true)
    public UserDto.Response updateUser(Long id, UserDto.UpdateRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + id));
        
        // 이메일 중복 검사 (다른 사용자와 중복되지 않도록)
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("이미 존재하는 이메일입니다: " + request.getEmail());
            }
            user.setEmail(request.getEmail());
        }
        
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        
        User updatedUser = userRepository.save(user);
        return convertToResponse(updatedUser);
    }
    
    @Override
    @CacheEvict(value = {"webUser", "webUserByUsername", "webAllUsers"}, allEntries = true)
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다: " + id);
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
    
    // User 엔티티를 Response DTO로 변환
    private UserDto.Response convertToResponse(User user) {
        UserDto.Response response = new UserDto.Response();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
