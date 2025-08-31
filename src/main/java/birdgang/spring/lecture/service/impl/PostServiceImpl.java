package birdgang.spring.lecture.service.impl;

import birdgang.spring.lecture.dto.PostDto;
import birdgang.spring.lecture.entity.Post;
import birdgang.spring.lecture.entity.User;
import birdgang.spring.lecture.repository.PostRepository;
import birdgang.spring.lecture.repository.UserRepository;
import birdgang.spring.lecture.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import birdgang.spring.lecture.dto.UserDto;

@Service
@Transactional
public class PostServiceImpl implements PostService {
    
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    
    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    
    @Override
    public PostDto.Response createPost(PostDto.CreateRequest request, Long authorId) {
        User author = userRepository.findById(authorId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + authorId));
        
        Post post = new Post(
            request.getTitle(),
            request.getContent(),
            author
        );
        
        Post savedPost = postRepository.save(post);
        return convertToResponse(savedPost);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PostDto.Response getPostById(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + id));
        return convertToResponse(post);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<PostDto.ListResponse> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
            .map(this::convertToListResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<PostDto.ListResponse> searchPosts(String keyword, Pageable pageable) {
        return postRepository.findByTitleOrContentContaining(keyword, pageable)
            .map(this::convertToListResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<PostDto.ListResponse> getPostsByAuthor(Long authorId, Pageable pageable) {
        return postRepository.findByAuthorId(authorId, pageable)
            .map(this::convertToListResponse);
    }
    
    @Override
    public PostDto.Response updatePost(Long id, PostDto.UpdateRequest request, Long authorId) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + id));
        
        // 작성자 확인
        if (!post.getAuthor().getId().equals(authorId)) {
            throw new IllegalArgumentException("게시글을 수정할 권한이 없습니다");
        }
        
        if (request.getTitle() != null) {
            post.setTitle(request.getTitle());
        }
        
        if (request.getContent() != null) {
            post.setContent(request.getContent());
        }
        
        Post updatedPost = postRepository.save(post);
        return convertToResponse(updatedPost);
    }
    
    @Override
    public void deletePost(Long id, Long authorId) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + id));
        
        // 작성자 확인
        if (!post.getAuthor().getId().equals(authorId)) {
            throw new IllegalArgumentException("게시글을 삭제할 권한이 없습니다");
        }
        
        postRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return postRepository.existsById(id);
    }
    
    // Post 엔티티를 Response DTO로 변환
    private PostDto.Response convertToResponse(Post post) {
        PostDto.Response response = new PostDto.Response();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        
        // User 정보 설정
        UserDto.Response authorResponse = new UserDto.Response();
        authorResponse.setId(post.getAuthor().getId());
        authorResponse.setUsername(post.getAuthor().getUsername());
        authorResponse.setEmail(post.getAuthor().getEmail());
        authorResponse.setFullName(post.getAuthor().getFullName());
        authorResponse.setCreatedAt(post.getAuthor().getCreatedAt());
        authorResponse.setUpdatedAt(post.getAuthor().getUpdatedAt());
        
        response.setAuthor(authorResponse);
        response.setCreatedAt(post.getCreatedAt());
        response.setUpdatedAt(post.getUpdatedAt());
        
        return response;
    }
    
    // Post 엔티티를 ListResponse DTO로 변환
    private PostDto.ListResponse convertToListResponse(Post post) {
        PostDto.ListResponse response = new PostDto.ListResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setAuthorUsername(post.getAuthor().getUsername());
        response.setCreatedAt(post.getCreatedAt());
        return response;
    }
}
