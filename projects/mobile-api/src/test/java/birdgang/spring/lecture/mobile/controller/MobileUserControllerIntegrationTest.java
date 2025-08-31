package birdgang.spring.lecture.mobile.controller;

import birdgang.spring.lecture.shared.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
class MobileUserControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        assert mockMvc != null;
    }

    @Test
    void registerUser_Success() throws Exception {
        // Given
        UserDto.CreateRequest request = new UserDto.CreateRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setFullName("Test User");

        // When & Then
        mockMvc.perform(post("/mobile/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.fullName").value("Test User"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void registerUser_ValidationError() throws Exception {
        // Given
        UserDto.CreateRequest request = new UserDto.CreateRequest();
        request.setUsername(""); // 빈 사용자명
        request.setEmail("invalid-email"); // 잘못된 이메일
        request.setPassword("123"); // 짧은 비밀번호

        // When & Then
        mockMvc.perform(post("/mobile/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"));
    }

    @Test
    void checkUsernameExists_True() throws Exception {
        // Given
        String username = "existinguser";

        // When & Then
        mockMvc.perform(get("/mobile/api/users/check/username/{username}", username))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void checkUsernameExists_False() throws Exception {
        // Given
        String username = "nonexistinguser";

        // When & Then
        mockMvc.perform(get("/mobile/api/users/check/username/{username}", username))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void checkEmailExists_True() throws Exception {
        // Given
        String email = "existing@example.com";

        // When & Then
        mockMvc.perform(get("/mobile/api/users/check/email/{email}", email))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void checkEmailExists_False() throws Exception {
        // Given
        String email = "nonexisting@example.com";

        // When & Then
        mockMvc.perform(get("/mobile/api/users/check/email/{email}", email))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}
