package birdgang.spring.lecture.shared.util;

import birdgang.spring.lecture.database.entity.Post;
import birdgang.spring.lecture.shared.dto.PostDto;

public class PostMapper {
    
    /**
     * Post 엔티티를 Response DTO로 변환
     */
    public static PostDto.Response toResponse(Post post) {
        if (post == null) {
            return null;
        }
        
        PostDto.Response response = new PostDto.Response();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setAuthor(UserMapper.toResponse(post.getAuthor()));
        response.setStatus(post.getStatus().name());
        response.setCategory(post.getCategory());
        response.setViewCount(post.getViewCount());
        response.setLikeCount(post.getLikeCount());
        response.setCreatedAt(post.getCreatedAt());
        response.setUpdatedAt(post.getUpdatedAt());
        response.setPublishedAt(post.getPublishedAt());
        return response;
    }
    
    /**
     * Post 엔티티를 ListResponse DTO로 변환
     */
    public static PostDto.ListResponse toListResponse(Post post) {
        if (post == null) {
            return null;
        }
        
        PostDto.ListResponse response = new PostDto.ListResponse();
        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setAuthorUsername(post.getAuthor() != null ? post.getAuthor().getUsername() : null);
        response.setStatus(post.getStatus().name());
        response.setCategory(post.getCategory());
        response.setViewCount(post.getViewCount());
        response.setLikeCount(post.getLikeCount());
        response.setCreatedAt(post.getCreatedAt());
        response.setPublishedAt(post.getPublishedAt());
        return response;
    }
    
    /**
     * CreateRequest DTO를 Post 엔티티로 변환
     */
    public static Post toEntity(PostDto.CreateRequest request) {
        if (request == null) {
            return null;
        }
        
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCategory(request.getCategory());
        return post;
    }
    
    /**
     * UpdateRequest DTO의 내용으로 Post 엔티티 업데이트
     */
    public static void updateEntityFromRequest(Post post, PostDto.UpdateRequest request) {
        if (post == null || request == null) {
            return;
        }
        
        if (request.getTitle() != null) {
            post.setTitle(request.getTitle());
        }
        
        if (request.getContent() != null) {
            post.setContent(request.getContent());
        }
        
        if (request.getCategory() != null) {
            post.setCategory(request.getCategory());
        }
    }
}
