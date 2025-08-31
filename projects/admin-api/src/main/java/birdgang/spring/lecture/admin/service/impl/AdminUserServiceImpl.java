package birdgang.spring.lecture.admin.service.impl;

import birdgang.spring.lecture.shared.dto.UserDto;
import birdgang.spring.lecture.database.entity.User;
import birdgang.spring.lecture.database.repository.UserRepository;
import birdgang.spring.lecture.admin.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
public class AdminUserServiceImpl implements AdminUserService {
    
    private final UserRepository userRepository;
    
    @Autowired
    public AdminUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "adminUsers", key = "#pageable.pageNumber + '_' + #pageable.pageSize + '_' + #pageable.sort")
    public Page<UserDto.Response> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
            .map(this::convertToResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "adminUser", key = "#id")
    public UserDto.Response getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + id));
        return convertToResponse(user);
    }
    
    @Override
    @CacheEvict(value = {"adminUser", "adminUsers"}, allEntries = true)
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
    @CacheEvict(value = {"adminUser", "adminUsers"}, allEntries = true)
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다: " + id);
        }
        userRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "adminUserSearch", key = "#keyword + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<UserDto.Response> searchUsers(String keyword, Pageable pageable) {
        // 간단한 검색 구현 (실제로는 더 복잡한 검색 로직 필요)
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(user -> {
            boolean matches = user.getUsername().toLowerCase().contains(keyword.toLowerCase()) ||
                user.getEmail().toLowerCase().contains(keyword.toLowerCase()) ||
                (user.getFullName() != null && user.getFullName().toLowerCase().contains(keyword.toLowerCase()));
            
            if (matches) {
                return convertToResponse(user);
            } else {
                return null;
            }
        });
    }
    
    @Override
    @CacheEvict(value = {"adminUser", "adminUsers"}, allEntries = true)
    public UserDto.Response activateUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + id));
        
        // 사용자 활성화 로직 (예: 상태 필드 추가 필요)
        // user.setActive(true);
        
        User activatedUser = userRepository.save(user);
        return convertToResponse(activatedUser);
    }
    
    @Override
    @CacheEvict(value = {"adminUser", "adminUsers"}, allEntries = true)
    public UserDto.Response deactivateUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + id));
        
        // 사용자 비활성화 로직 (예: 상태 필드 추가 필요)
        // user.setActive(false);
        
        User deactivatedUser = userRepository.save(user);
        return convertToResponse(deactivatedUser);
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
