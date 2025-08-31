package birdgang.spring.lecture.mobile.service;

import birdgang.spring.lecture.shared.dto.UserDto;
import birdgang.spring.lecture.database.entity.User;
import birdgang.spring.lecture.database.repository.UserRepository;
import birdgang.spring.lecture.mobile.service.impl.MobileUserServiceImpl;
import birdgang.spring.lecture.common.exception.DuplicateResourceException;
import birdgang.spring.lecture.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MobileUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MobileUserServiceImpl mobileUserService;

    private UserDto.CreateRequest createRequest;
    private UserDto.UpdateRequest updateRequest;
    private User testUser;

    @BeforeEach
    void setUp() {
        createRequest = new UserDto.CreateRequest();
        createRequest.setUsername("testuser");
        createRequest.setEmail("test@example.com");
        createRequest.setPassword("password123");
        createRequest.setFullName("Test User");

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
    }

    @Test
    void registerUser_Success() {
        // Given
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        UserDto.Response response = mobileUserService.registerUser(createRequest);

        // Then
        assertNotNull(response);
        assertEquals(testUser.getId(), response.getId());
        assertEquals(testUser.getUsername(), response.getUsername());
        assertEquals(testUser.getEmail(), response.getEmail());
        assertEquals(testUser.getFullName(), response.getFullName());
        assertTrue(response.isActive());

        verify(userRepository).existsByUsername("testuser");
        verify(userRepository).existsByEmail("test@example.com");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUser_DuplicateUsername() {
        // Given
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // When & Then
        assertThrows(DuplicateResourceException.class, () -> {
            mobileUserService.registerUser(createRequest);
        });

        verify(userRepository).existsByUsername("testuser");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUser_DuplicateEmail() {
        // Given
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // When & Then
        assertThrows(DuplicateResourceException.class, () -> {
            mobileUserService.registerUser(createRequest);
        });

        verify(userRepository).existsByUsername("testuser");
        verify(userRepository).existsByEmail("test@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getUserProfile_Success() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // When
        UserDto.Response response = mobileUserService.getUserProfile(1L);

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
    void getUserProfile_UserNotFound() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            mobileUserService.getUserProfile(1L);
        });

        verify(userRepository).findById(1L);
    }

    @Test
    void updateProfile_Success() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.existsByEmail("updated@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        UserDto.Response response = mobileUserService.updateProfile(1L, updateRequest);

        // Then
        assertNotNull(response);
        verify(userRepository).findById(1L);
        verify(userRepository).existsByEmail("updated@example.com");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateProfile_UserNotFound() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            mobileUserService.updateProfile(1L, updateRequest);
        });

        verify(userRepository).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateProfile_DuplicateEmail() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.existsByEmail("updated@example.com")).thenReturn(true);

        // When & Then
        assertThrows(DuplicateResourceException.class, () -> {
            mobileUserService.updateProfile(1L, updateRequest);
        });

        verify(userRepository).findById(1L);
        verify(userRepository).existsByEmail("updated@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void existsByUsername_True() {
        // Given
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        // When
        boolean exists = mobileUserService.existsByUsername("testuser");

        // Then
        assertTrue(exists);
        verify(userRepository).existsByUsername("testuser");
    }

    @Test
    void existsByUsername_False() {
        // Given
        when(userRepository.existsByUsername("testuser")).thenReturn(false);

        // When
        boolean exists = mobileUserService.existsByUsername("testuser");

        // Then
        assertFalse(exists);
        verify(userRepository).existsByUsername("testuser");
    }

    @Test
    void existsByEmail_True() {
        // Given
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        // When
        boolean exists = mobileUserService.existsByEmail("test@example.com");

        // Then
        assertTrue(exists);
        verify(userRepository).existsByEmail("test@example.com");
    }

    @Test
    void existsByEmail_False() {
        // Given
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);

        // When
        boolean exists = mobileUserService.existsByEmail("test@example.com");

        // Then
        assertFalse(exists);
        verify(userRepository).existsByEmail("test@example.com");
    }
}
