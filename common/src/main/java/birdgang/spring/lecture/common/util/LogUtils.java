package birdgang.spring.lecture.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;
import java.util.UUID;

public class LogUtils {
    
    private static final String REQUEST_ID_KEY = "requestId";
    private static final String USER_ID_KEY = "userId";
    private static final String API_VERSION_KEY = "apiVersion";
    private static final String OPERATION_KEY = "operation";
    
    /**
     * 요청 ID 생성 및 설정
     */
    public static String setRequestId() {
        String requestId = UUID.randomUUID().toString().substring(0, 8);
        MDC.put(REQUEST_ID_KEY, requestId);
        return requestId;
    }
    
    /**
     * 사용자 ID 설정
     */
    public static void setUserId(String userId) {
        if (userId != null) {
            MDC.put(USER_ID_KEY, userId);
        }
    }
    
    /**
     * API 버전 설정
     */
    public static void setApiVersion(String apiVersion) {
        if (apiVersion != null) {
            MDC.put(API_VERSION_KEY, apiVersion);
        }
    }
    
    /**
     * 작업 유형 설정
     */
    public static void setOperation(String operation) {
        if (operation != null) {
            MDC.put(OPERATION_KEY, operation);
        }
    }
    
    /**
     * 모든 컨텍스트 정보 설정
     */
    public static void setContext(String requestId, String userId, String apiVersion, String operation) {
        if (requestId != null) MDC.put(REQUEST_ID_KEY, requestId);
        if (userId != null) MDC.put(USER_ID_KEY, userId);
        if (apiVersion != null) MDC.put(API_VERSION_KEY, apiVersion);
        if (operation != null) MDC.put(OPERATION_KEY, operation);
    }
    
    /**
     * 컨텍스트 정보 초기화
     */
    public static void clearContext() {
        MDC.clear();
    }
    
    /**
     * 현재 요청 ID 조회
     */
    public static String getRequestId() {
        return MDC.get(REQUEST_ID_KEY);
    }
    
    /**
     * 현재 사용자 ID 조회
     */
    public static String getUserId() {
        return MDC.get(USER_ID_KEY);
    }
    
    /**
     * 현재 API 버전 조회
     */
    public static String getApiVersion() {
        return MDC.get(API_VERSION_KEY);
    }
    
    /**
     * 현재 작업 유형 조회
     */
    public static String getOperation() {
        return MDC.get(OPERATION_KEY);
    }
    
    /**
     * 로거 생성 (클래스별)
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
    
    /**
     * 로거 생성 (이름별)
     */
    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }
    
    /**
     * 성능 측정을 위한 로깅 유틸리티
     */
    public static class Performance {
        private static final Logger logger = LoggerFactory.getLogger("PERFORMANCE");
        
        public static void logExecutionTime(String operation, long startTime) {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.info("Operation: {}, ExecutionTime: {}ms", operation, executionTime);
        }
        
        public static void logDatabaseQuery(String query, long executionTime) {
            logger.info("DatabaseQuery: {}, ExecutionTime: {}ms", query, executionTime);
        }
        
        public static void logCacheHit(String cacheKey) {
            logger.debug("CacheHit: {}", cacheKey);
        }
        
        public static void logCacheMiss(String cacheKey) {
            logger.debug("CacheMiss: {}", cacheKey);
        }
    }
    
    /**
     * 보안 관련 로깅 유틸리티
     */
    public static class Security {
        private static final Logger logger = LoggerFactory.getLogger("SECURITY");
        
        public static void logAuthenticationSuccess(String username) {
            logger.info("AuthenticationSuccess: {}", username);
        }
        
        public static void logAuthenticationFailure(String username, String reason) {
            logger.warn("AuthenticationFailure: {}, Reason: {}", username, reason);
        }
        
        public static void logAuthorizationFailure(String username, String resource, String action) {
            logger.warn("AuthorizationFailure: {}, Resource: {}, Action: {}", username, resource, action);
        }
        
        public static void logSuspiciousActivity(String username, String activity) {
            logger.error("SuspiciousActivity: {}, Activity: {}", username, activity);
        }
    }
    
    /**
     * 비즈니스 로직 로깅 유틸리티
     */
    public static class Business {
        private static final Logger logger = LoggerFactory.getLogger("BUSINESS");
        
        public static void logUserCreated(String username, String email) {
            logger.info("UserCreated: {}, Email: {}", username, email);
        }
        
        public static void logUserUpdated(String username, String field) {
            logger.info("UserUpdated: {}, Field: {}", username, field);
        }
        
        public static void logUserDeleted(String username) {
            logger.info("UserDeleted: {}", username);
        }
        
        public static void logPostCreated(String title, String author) {
            logger.info("PostCreated: {}, Author: {}", title, author);
        }
        
        public static void logPostPublished(String title, String author) {
            logger.info("PostPublished: {}, Author: {}", title, author);
        }
    }
}
