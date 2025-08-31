package birdgang.spring.lecture.admin.controller;

import birdgang.spring.lecture.shared.dto.UserDto;
import birdgang.spring.lecture.admin.service.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/users")
@Tag(name = "Admin User API", description = "관리자 사용자 관리 API")
public class AdminUserController {
    
    private final AdminUserService adminUserService;
    
    @Autowired
    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }
    
    @GetMapping
    @Operation(summary = "사용자 목록 조회 (관리자)", description = "페이징을 통해 모든 사용자를 조회합니다.")
    public ResponseEntity<Page<UserDto.Response>> getAllUsers(
        @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
        @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "페이지 크기", example = "20")
        @RequestParam(defaultValue = "20") int size,
        @Parameter(description = "정렬 기준", example = "createdAt")
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @Parameter(description = "정렬 방향 (asc/desc)", example = "desc")
        @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? 
            Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        
        Page<UserDto.Response> response = adminUserService.getAllUsers(pageable);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "사용자 상세 조회 (관리자)", description = "ID로 사용자 상세 정보를 조회합니다.")
    public ResponseEntity<UserDto.Response> getUserById(
        @Parameter(description = "사용자 ID", example = "1")
        @PathVariable Long id
    ) {
        UserDto.Response response = adminUserService.getUserById(id);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "사용자 정보 수정 (관리자)", description = "관리자 권한으로 사용자 정보를 수정합니다.")
    public ResponseEntity<UserDto.Response> updateUser(
        @Parameter(description = "사용자 ID", example = "1")
        @PathVariable Long id,
        @Valid @RequestBody UserDto.UpdateRequest request
    ) {
        UserDto.Response response = adminUserService.updateUser(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "사용자 삭제 (관리자)", description = "관리자 권한으로 사용자를 삭제합니다.")
    public ResponseEntity<Void> deleteUser(
        @Parameter(description = "사용자 ID", example = "1")
        @PathVariable Long id
    ) {
        adminUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    @Operation(summary = "사용자 검색 (관리자)", description = "키워드로 사용자를 검색합니다.")
    public ResponseEntity<Page<UserDto.Response>> searchUsers(
        @Parameter(description = "검색 키워드 (사용자명, 이메일, 이름)", example = "john")
        @RequestParam String keyword,
        @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
        @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "페이지 크기", example = "20")
        @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<UserDto.Response> response = adminUserService.searchUsers(keyword, pageable);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{id}/activate")
    @Operation(summary = "사용자 활성화 (관리자)", description = "사용자 계정을 활성화합니다.")
    public ResponseEntity<UserDto.Response> activateUser(
        @Parameter(description = "사용자 ID", example = "1")
        @PathVariable Long id
    ) {
        UserDto.Response response = adminUserService.activateUser(id);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{id}/deactivate")
    @Operation(summary = "사용자 비활성화 (관리자)", description = "사용자 계정을 비활성화합니다.")
    public ResponseEntity<UserDto.Response> deactivateUser(
        @Parameter(description = "사용자 ID", example = "1")
        @PathVariable Long id
    ) {
        UserDto.Response response = adminUserService.deactivateUser(id);
        return ResponseEntity.ok(response);
    }
}
