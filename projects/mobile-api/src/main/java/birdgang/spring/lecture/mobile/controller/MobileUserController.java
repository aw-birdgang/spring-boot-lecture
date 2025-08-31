package birdgang.spring.lecture.mobile.controller;

import birdgang.spring.lecture.shared.dto.UserDto;
import birdgang.spring.lecture.mobile.service.MobileUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mobile/api/users")
@Tag(name = "Mobile User API", description = "모바일 사용자 관리 API")
public class MobileUserController {
    
    private final MobileUserService mobileUserService;
    
    @Autowired
    public MobileUserController(MobileUserService mobileUserService) {
        this.mobileUserService = mobileUserService;
    }
    
    @PostMapping("/register")
    @Operation(summary = "모바일 사용자 등록", description = "새로운 모바일 사용자를 등록합니다.")
    public ResponseEntity<UserDto.Response> registerUser(
        @Valid @RequestBody UserDto.CreateRequest request
    ) {
        UserDto.Response response = mobileUserService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/profile/{id}")
    @Operation(summary = "사용자 프로필 조회", description = "ID로 사용자 프로필을 조회합니다.")
    public ResponseEntity<UserDto.Response> getUserProfile(
        @Parameter(description = "사용자 ID", example = "1")
        @PathVariable Long id
    ) {
        UserDto.Response response = mobileUserService.getUserProfile(id);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/profile/{id}")
    @Operation(summary = "프로필 수정", description = "사용자 프로필을 수정합니다.")
    public ResponseEntity<UserDto.Response> updateProfile(
        @Parameter(description = "사용자 ID", example = "1")
        @PathVariable Long id,
        @Valid @RequestBody UserDto.UpdateRequest request
    ) {
        UserDto.Response response = mobileUserService.updateProfile(id, request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/check/username/{username}")
    @Operation(summary = "사용자명 중복 확인", description = "사용자명의 중복 여부를 확인합니다.")
    public ResponseEntity<Boolean> checkUsernameExists(
        @Parameter(description = "확인할 사용자명", example = "john_doe")
        @PathVariable String username
    ) {
        boolean exists = mobileUserService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }
    
    @GetMapping("/check/email/{email}")
    @Operation(summary = "이메일 중복 확인", description = "이메일의 중복 여부를 확인합니다.")
    public ResponseEntity<Boolean> checkEmailExists(
        @Parameter(description = "확인할 이메일", example = "john@example.com")
        @PathVariable String email
    ) {
        boolean exists = mobileUserService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
}
