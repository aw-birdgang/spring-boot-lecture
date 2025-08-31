package birdgang.spring.lecture.admin.service;

import birdgang.spring.lecture.shared.dto.UserDto;
import birdgang.spring.lecture.database.entity.User;
import birdgang.spring.lecture.database.repository.UserRepository;
import birdgang.spring.lecture.admin.service.impl.AdminUserServiceImpl;
import birdgang.spring.lecture.common.exception.DuplicateResourceException;
import birdgang.spring.lecture.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminUserServiceImpl adminUserService;

    private UserDto.UpdateRequest updateRequest;
    private User testUser;
    private User testUser2;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        updateRequest = new UserDto.UpdateRequest();
        updateRequest.setFullName("Updated User");
        updateRequest.setEmail("updated@example.com");

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("encodedPassword");
        testUser.setFullName("Test User");
        testUser.setActive(true);
        testUser.setCreatedAt(LocalDateTime.now());
        testUser.setUpdatedAt(LocalDateTime.now());

        testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("testuser2");
        testUser2.setEmail("test2@example.com");
        testUser2.setPassword("encodedPassword2");
        testUser2.setFullName("Test User 2");
        testUser2.setActive(true);
        testUser2.setCreatedAt(LocalDateTime.now());
        testUser2.setUpdatedAt(LocalDateTime.now());

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void getAllUsers_Success() {
        // Given
        List<User> users = Arrays.asList(testUser, testUser2);
        Page<User> userPage = new PageImpl<>(users, pageable, 2);
        when(userRepository.findAll(pageable)).thenReturn(userPage);

        // When
        Page<UserDto.Response> response = adminUserService.getAllUsers(pageable);

        // Then
        assertNotNull(response);
        assertEquals(2, response.getTotalElements());
        assertEquals(2, response.getContent().size());
        assertEquals(testUser.getUsername(), response.getContent().get(0).getUsername());
        assertEquals(testUser2.getUsername(), response.getContent().get(1).getUsername());

        verify(userRepository).findAll(pageable);
    }

    @Test
    void getUserById_Success() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // When
        UserDto.Response response = adminUserService.getUserById(1L);

        // Then
        assertNotNull(response);
        assertEquals(testUser.getId(), response.getId());
        assertEquals(testUser.getUsername(), response.getUsername());
        assertEquals(testUser.getEmail(), response.getEmail());
        assertEquals(testUser.getFullName(), response.getFullName());
        assertTrue(response.isActive());

        verify(userRepository).findById(1L);
    }

    @Test
    void getUserById_UserNotFound() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            adminUserService.getUserById(1L);
        });

        verify(userRepository).findById(1L);
    }

    @Test
    void updateUser_Success() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.existsByEmail("updated@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        UserDto.Response response = adminUserService.updateUser(1L, updateRequest);

        // Then
        assertNotNull(response);
        verify(userRepository).findById(1L);
        verify(userRepository).existsByEmail("updated@example.com");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_UserNotFound() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            adminUserService.updateUser(1L, updateRequest);
        });

        verify(userRepository).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_DuplicateEmail() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.existsByEmail("updated@example.com")).thenReturn(true);

        // When & Then
        assertThrows(DuplicateResourceException.class, () -> {
            adminUserService.updateUser(1L, updateRequest);
        });

        verify(userRepository).findById(1L);
        verify(userRepository).existsByEmail("updated@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUser_Success() {
        // Given
        when(userRepository.existsById(1L)).thenReturn(true);

        // When
        adminUserService.deleteUser(1L);

        // Then
        verify(userRepository).existsById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_UserNotFound() {
        // Given
        when(userRepository.existsById(1L)).thenReturn(false);

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            adminUserService.deleteUser(1L);
        });

        verify(userRepository).existsById(1L);
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    void searchUsers_Success() {
        // Given
        List<User> users = Arrays.asList(testUser, testUser2);
        Page<User> userPage = new PageImpl<>(users, pageable, 2);
        when(userRepository.searchUsers(anyString(), any(), any(Pageable.class))).thenReturn(userPage);

        // When
        Page<UserDto.Response> response = adminUserService.searchUsers("test", pageable);

        // Then
        assertNotNull(response);
        assertEquals(2, response.getTotalElements());
        assertEquals(2, response.getContent().size());

        verify(userRepository).searchUsers("test", null, pageable);
    }

    @Test
    void activateUser_Success() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        UserDto.Response response = adminUserService.activateUser(1L);

        // Then
        assertNotNull(response);
        assertTrue(testUser.isActive());
        verify(userRepository).findById(1L);
        verify(userRepository).save(testUser);
    }

    @Test
    void activateUser_UserNotFound() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            adminUserService.activateUser(1L);
        });

        verify(userRepository).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deactivateUser_Success() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        UserDto.Response response = adminUserService.deactivateUser(1L);

        // Then
        assertNotNull(response);
        assertFalse(testUser.isActive());
        verify(userRepository).findById(1L);
        verify(userRepository).save(testUser);
    }

    @Test
    void deactivateUser_UserNotFound() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            adminUserService.deactivateUser(1L);
        });

        verify(userRepository).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }
}
