package birdgang.spring.lecture.web.service;

import birdgang.spring.lecture.shared.dto.UserDto;
import birdgang.spring.lecture.database.entity.User;
import birdgang.spring.lecture.database.repository.UserRepository;
import birdgang.spring.lecture.web.service.impl.WebUserServiceImpl;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private WebUserServiceImpl webUserService;

    private UserDto.CreateRequest createRequest;
    private UserDto.UpdateRequest updateRequest;
    private User testUser;
    private User testUser2;

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

        testUser2 = new User();
        testUser2.setId(2L);
        testUser2.setUsername("testuser2");
        testUser2.setEmail("test2@example.com");
        testUser2.setPassword("encodedPassword2");
        testUser2.setFullName("Test User 2");
        testUser2.setActive(true);
        testUser2.setCreatedAt(LocalDateTime.now());
        testUser2.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void createUser_Success() {
        // Given
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        UserDto.Response response = webUserService.createUser(createRequest);

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
    void createUser_DuplicateUsername() {
        // Given
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // When & Then
        assertThrows(DuplicateResourceException.class, () -> {
            webUserService.createUser(createRequest);
        });

        verify(userRepository).existsByUsername("testuser");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void createUser_DuplicateEmail() {
        // Given
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // When & Then
        assertThrows(DuplicateResourceException.class, () -> {
            webUserService.createUser(createRequest);
        });

        verify(userRepository).existsByUsername("testuser");
        verify(userRepository).existsByEmail("test@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getUserById_Success() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // When
        UserDto.Response response = webUserService.getUserById(1L);

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
            webUserService.getUserById(1L);
        });

        verify(userRepository).findById(1L);
    }

    @Test
    void getUserByUsername_Success() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        // When
        UserDto.Response response = webUserService.getUserByUsername("testuser");

        // Then
        assertNotNull(response);
        assertEquals(testUser.getId(), response.getId());
        assertEquals(testUser.getUsername(), response.getUsername());
        assertEquals(testUser.getEmail(), response.getEmail());
        assertEquals(testUser.getFullName(), response.getFullName());
        assertTrue(response.isActive());

        verify(userRepository).findByUsername("testuser");
    }

    @Test
    void getUserByUsername_UserNotFound() {
        // Given
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            webUserService.getUserByUsername("testuser");
        });

        verify(userRepository).findByUsername("testuser");
    }

    @Test
    void getAllUsers_Success() {
        // Given
        List<User> users = Arrays.asList(testUser, testUser2);
        when(userRepository.findAll()).thenReturn(users);

        // When
        List<UserDto.Response> response = webUserService.getAllUsers();

        // Then
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(testUser.getUsername(), response.get(0).getUsername());
        assertEquals(testUser2.getUsername(), response.get(1).getUsername());

        verify(userRepository).findAll();
    }

    @Test
    void updateUser_Success() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.existsByEmail("updated@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        UserDto.Response response = webUserService.updateUser(1L, updateRequest);

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
            webUserService.updateUser(1L, updateRequest);
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
            webUserService.updateUser(1L, updateRequest);
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
        webUserService.deleteUser(1L);

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
            webUserService.deleteUser(1L);
        });

        verify(userRepository).existsById(1L);
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    void existsByUsername_True() {
        // Given
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        // When
        boolean exists = webUserService.existsByUsername("testuser");

        // Then
        assertTrue(exists);
        verify(userRepository).existsByUsername("testuser");
    }

    @Test
    void existsByUsername_False() {
        // Given
        when(userRepository.existsByUsername("testuser")).thenReturn(false);

        // When
        boolean exists = webUserService.existsByUsername("testuser");

        // Then
        assertFalse(exists);
        verify(userRepository).existsByUsername("testuser");
    }

    @Test
    void existsByEmail_True() {
        // Given
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        // When
        boolean exists = webUserService.existsByEmail("test@example.com");

        // Then
        assertTrue(exists);
        verify(userRepository).existsByEmail("test@example.com");
    }

    @Test
    void existsByEmail_False() {
        // Given
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);

        // When
        boolean exists = webUserService.existsByEmail("test@example.com");

        // Then
        assertFalse(exists);
        verify(userRepository).existsByEmail("test@example.com");
    }
}
