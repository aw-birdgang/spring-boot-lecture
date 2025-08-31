package birdgang.spring.lecture.common.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CacheKeyGenerator {
    
    /**
     * 단일 값으로 캐시 키 생성
     */
    public static String generateKey(String prefix, Object value) {
        return String.format("%s:%s", prefix, value);
    }
    
    /**
     * 여러 값으로 복합 캐시 키 생성
     */
    public static String generateKey(String prefix, Object... values) {
        String valuesStr = Arrays.stream(values)
            .map(Object::toString)
            .collect(Collectors.joining(":"));
        return String.format("%s:%s", prefix, valuesStr);
    }
    
    /**
     * 페이징 정보를 포함한 캐시 키 생성
     */
    public static String generatePageKey(String prefix, int page, int size, String sort) {
        return String.format("%s:page:%d:size:%d:sort:%s", prefix, page, size, sort);
    }
    
    /**
     * 검색 조건을 포함한 캐시 키 생성
     */
    public static String generateSearchKey(String prefix, String keyword, String category, Long authorId) {
        return String.format("%s:search:keyword:%s:category:%s:author:%s", 
            prefix, 
            keyword != null ? keyword : "null",
            category != null ? category : "null",
            authorId != null ? authorId : "null");
    }
    
    /**
     * 사용자 관련 캐시 키 생성
     */
    public static class UserKeys {
        public static String profile(Long userId) {
            return generateKey("user", "profile", userId);
        }
        
        public static String byUsername(String username) {
            return generateKey("user", "username", username);
        }
        
        public static String byEmail(String email) {
            return generateKey("user", "email", email);
        }
        
        public static String all() {
            return "user:all";
        }
        
        public static String search(String keyword) {
            return generateKey("user", "search", keyword);
        }
        
        public static String page(int page, int size, String sort) {
            return generatePageKey("user", page, size, sort);
        }
    }
    
    /**
     * 게시글 관련 캐시 키 생성
     */
    public static class PostKeys {
        public static String byId(Long postId) {
            return generateKey("post", "id", postId);
        }
        
        public static String byAuthor(Long authorId) {
            return generateKey("post", "author", authorId);
        }
        
        public static String byCategory(String category) {
            return generateKey("post", "category", category);
        }
        
        public static String search(String keyword, String category, Long authorId) {
            return generateSearchKey("post", keyword, category, authorId);
        }
        
        public static String recent() {
            return "post:recent";
        }
        
        public static String popular() {
            return "post:popular";
        }
        
        public static String page(int page, int size, String sort) {
            return generatePageKey("post", page, size, sort);
        }
    }
}
