package birdgang.spring.lecture.admin.service.impl;

import birdgang.spring.lecture.shared.dto.UserDto;
import birdgang.spring.lecture.shared.util.UserMapper;
import birdgang.spring.lecture.database.entity.User;
import birdgang.spring.lecture.database.repository.UserRepository;
import birdgang.spring.lecture.admin.service.AdminUserService;
import birdgang.spring.lecture.common.exception.DuplicateResourceException;
import birdgang.spring.lecture.common.exception.ResourceNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminUserServiceImpl implements AdminUserService {
    
    private final UserRepository userRepository;
    
    public AdminUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "adminUsers", key = "#pageable.pageNumber + '_' + #pageable.pageSize + '_' + #pageable.sort")
    public Page<UserDto.Response> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
            .map(UserMapper::toResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "adminUser", key = "#id")
    public UserDto.Response getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return UserMapper.toResponse(user);
    }
    
    @Override
    @CacheEvict(value = {"adminUser", "adminUsers"}, allEntries = true)
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
    @CacheEvict(value = {"adminUser", "adminUsers"}, allEntries = true)
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        userRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "adminUserSearch", key = "#keyword + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<UserDto.Response> searchUsers(String keyword, Pageable pageable) {
        // 효율적인 검색 구현
        return userRepository.searchUsers(keyword, null, pageable)
            .map(UserMapper::toResponse);
    }
    
    @Override
    @CacheEvict(value = {"adminUser", "adminUsers"}, allEntries = true)
    public UserDto.Response activateUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        user.setActive(true);
        User activatedUser = userRepository.save(user);
        return UserMapper.toResponse(activatedUser);
    }
    
    @Override
    @CacheEvict(value = {"adminUser", "adminUsers"}, allEntries = true)
    public UserDto.Response deactivateUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        user.setActive(false);
        User deactivatedUser = userRepository.save(user);
        return UserMapper.toResponse(deactivatedUser);
    }
}
