package birdgang.spring.lecture.common.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {
    
    // 캐시 키 상수 정의
    public static final String USER_PROFILE_CACHE = "userProfile";
    public static final String USER_BY_USERNAME_CACHE = "userByUsername";
    public static final String USER_BY_EMAIL_CACHE = "userByEmail";
    public static final String ALL_USERS_CACHE = "allUsers";
    public static final String USER_SEARCH_CACHE = "userSearch";
    public static final String POST_CACHE = "post";
    public static final String POST_LIST_CACHE = "postList";
    public static final String POST_SEARCH_CACHE = "postSearch";
    
    @Bean
    @Primary
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .recordStats());
        
        // 특정 캐시별 설정
        cacheManager.registerCustomCache(USER_PROFILE_CACHE, 
            Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .recordStats()
                .build());
        
        cacheManager.registerCustomCache(ALL_USERS_CACHE,
            Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .expireAfterAccess(3, TimeUnit.MINUTES)
                .recordStats()
                .build());
        
        cacheManager.registerCustomCache(POST_CACHE,
            Caffeine.newBuilder()
                .maximumSize(200)
                .expireAfterWrite(20, TimeUnit.MINUTES)
                .expireAfterAccess(15, TimeUnit.MINUTES)
                .recordStats()
                .build());
        
        return cacheManager;
    }
}
