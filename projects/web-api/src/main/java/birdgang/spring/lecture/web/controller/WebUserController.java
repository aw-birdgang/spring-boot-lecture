package birdgang.spring.lecture.web.controller;

import birdgang.spring.lecture.shared.dto.UserDto;
import birdgang.spring.lecture.web.service.WebUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/web/api/users")
@Tag(name = "Web User API", description = "웹 사용자 관리 API")
public class WebUserController {
    
    private final WebUserService webUserService;
    
    public WebUserController(WebUserService webUserService) {
        this.webUserService = webUserService;
    }
    
    @PostMapping
    @Operation(summary = "웹 사용자 생성", description = "새로운 웹 사용자를 생성합니다.")
    public ResponseEntity<UserDto.Response> createUser(
        @Valid @RequestBody UserDto.CreateRequest request
    ) {
        UserDto.Response response = webUserService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "웹 사용자 조회", description = "ID로 웹 사용자를 조회합니다.")
    public ResponseEntity<UserDto.Response> getUserById(
        @Parameter(description = "사용자 ID", example = "1")
        @PathVariable Long id
    ) {
        UserDto.Response response = webUserService.getUserById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/username/{username}")
    @Operation(summary = "사용자명으로 조회", description = "사용자명으로 웹 사용자를 조회합니다.")
    public ResponseEntity<UserDto.Response> getUserByUsername(
        @Parameter(description = "사용자명", example = "john_doe")
        @PathVariable String username
    ) {
        UserDto.Response response = webUserService.getUserByUsername(username);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "전체 웹 사용자 조회", description = "모든 웹 사용자를 조회합니다.")
    public ResponseEntity<List<UserDto.Response>> getAllUsers() {
        List<UserDto.Response> response = webUserService.getAllUsers();
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "웹 사용자 정보 수정", description = "웹 사용자 정보를 수정합니다.")
    public ResponseEntity<UserDto.Response> updateUser(
        @Parameter(description = "사용자 ID", example = "1")
        @PathVariable Long id,
        @Valid @RequestBody UserDto.UpdateRequest request
    ) {
        UserDto.Response response = webUserService.updateUser(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "웹 사용자 삭제", description = "웹 사용자를 삭제합니다.")
    public ResponseEntity<Void> deleteUser(
        @Parameter(description = "사용자 ID", example = "1")
        @PathVariable Long id
    ) {
        webUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/check/username/{username}")
    @Operation(summary = "사용자명 중복 확인", description = "사용자명의 중복 여부를 확인합니다.")
    public ResponseEntity<Boolean> checkUsernameExists(
        @Parameter(description = "확인할 사용자명", example = "john_doe")
        @PathVariable String username
    ) {
        boolean exists = webUserService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }
    
    @GetMapping("/check/email/{email}")
    @Operation(summary = "이메일 중복 확인", description = "이메일의 중복 여부를 확인합니다.")
    public ResponseEntity<Boolean> checkEmailExists(
        @Parameter(description = "확인할 이메일", example = "john@example.com")
        @PathVariable String email
    ) {
        boolean exists = webUserService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
}
