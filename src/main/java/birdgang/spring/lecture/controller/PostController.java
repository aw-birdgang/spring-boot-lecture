package birdgang.spring.lecture.controller;

import birdgang.spring.lecture.dto.PostDto;
import birdgang.spring.lecture.service.PostService;
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
@RequestMapping("/api/posts")
@Tag(name = "Post API", description = "게시글 관리 API")
public class PostController {
    
    private final PostService postService;
    
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    
    @PostMapping
    @Operation(summary = "게시글 생성", description = "새로운 게시글을 생성합니다.")
    public ResponseEntity<PostDto.Response> createPost(
        @Valid @RequestBody PostDto.CreateRequest request,
        @Parameter(description = "작성자 ID", example = "1")
        @RequestParam Long authorId
    ) {
        PostDto.Response response = postService.createPost(request, authorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "게시글 조회", description = "ID로 게시글을 조회합니다.")
    public ResponseEntity<PostDto.Response> getPostById(
        @Parameter(description = "게시글 ID", example = "1")
        @PathVariable Long id
    ) {
        PostDto.Response response = postService.getPostById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "게시글 목록 조회", description = "게시글 목록을 페이징하여 조회합니다.")
    public ResponseEntity<Page<PostDto.ListResponse>> getPosts(
        @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
        @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "페이지 크기", example = "10")
        @RequestParam(defaultValue = "10") int size,
        @Parameter(description = "정렬 기준", example = "createdAt")
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @Parameter(description = "정렬 방향 (asc/desc)", example = "desc")
        @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? 
            Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        
        Page<PostDto.ListResponse> response = postService.getPosts(pageable);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search")
    @Operation(summary = "게시글 검색", description = "키워드로 게시글을 검색합니다.")
    public ResponseEntity<Page<PostDto.ListResponse>> searchPosts(
        @Parameter(description = "검색 키워드", example = "Spring Boot")
        @RequestParam String keyword,
        @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
        @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "페이지 크기", example = "10")
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<PostDto.ListResponse> response = postService.searchPosts(keyword, pageable);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/author/{authorId}")
    @Operation(summary = "작성자별 게시글 조회", description = "특정 사용자가 작성한 게시글을 조회합니다.")
    public ResponseEntity<Page<PostDto.ListResponse>> getPostsByAuthor(
        @Parameter(description = "작성자 ID", example = "1")
        @PathVariable Long authorId,
        @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
        @RequestParam(defaultValue = "0") int page,
        @Parameter(description = "페이지 크기", example = "10")
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<PostDto.ListResponse> response = postService.getPostsByAuthor(authorId, pageable);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    public ResponseEntity<PostDto.Response> updatePost(
        @Parameter(description = "게시글 ID", example = "1")
        @PathVariable Long id,
        @Valid @RequestBody PostDto.UpdateRequest request,
        @Parameter(description = "작성자 ID", example = "1")
        @RequestParam Long authorId
    ) {
        PostDto.Response response = postService.updatePost(id, request, authorId);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    public ResponseEntity<Void> deletePost(
        @Parameter(description = "게시글 ID", example = "1")
        @PathVariable Long id,
        @Parameter(description = "작성자 ID", example = "1")
        @RequestParam Long authorId
    ) {
        postService.deletePost(id, authorId);
        return ResponseEntity.noContent().build();
    }
}
