package birdgang.spring.lecture.service;

import birdgang.spring.lecture.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    
    // 게시글 생성
    PostDto.Response createPost(PostDto.CreateRequest request, Long authorId);
    
    // 게시글 조회 (ID로)
    PostDto.Response getPostById(Long id);
    
    // 게시글 목록 조회 (페이징)
    Page<PostDto.ListResponse> getPosts(Pageable pageable);
    
    // 게시글 검색 (제목 또는 내용)
    Page<PostDto.ListResponse> searchPosts(String keyword, Pageable pageable);
    
    // 특정 사용자의 게시글 조회
    Page<PostDto.ListResponse> getPostsByAuthor(Long authorId, Pageable pageable);
    
    // 게시글 수정
    PostDto.Response updatePost(Long id, PostDto.UpdateRequest request, Long authorId);
    
    // 게시글 삭제
    void deletePost(Long id, Long authorId);
    
    // 게시글 존재 여부 확인
    boolean existsById(Long id);
}
