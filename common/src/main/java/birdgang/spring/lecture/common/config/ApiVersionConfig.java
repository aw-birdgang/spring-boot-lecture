package birdgang.spring.lecture.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.context.annotation.Bean;

@Configuration
public class ApiVersionConfig implements WebMvcConfigurer {
    
    /**
     * API 버전 정보를 담는 상수 클래스
     */
    public static class ApiVersions {
        public static final String V1 = "v1";
        public static final String V2 = "v2";
        public static final String LATEST = V1; // 현재 최신 버전
        
        // 지원하는 버전 목록
        public static final String[] SUPPORTED_VERSIONS = {V1, V2};
    }
    
    /**
     * API 경로 패턴 정의
     */
    public static class ApiPaths {
        public static final String V1_PATH = "/api/v1";
        public static final String V2_PATH = "/api/v2";
        public static final String LATEST_PATH = V1_PATH;
    }
    
    /**
     * API 버전별 기본 경로 매핑
     */
    public static String getApiPath(String version) {
        return switch (version) {
            case ApiVersions.V1 -> ApiPaths.V1_PATH;
            case ApiVersions.V2 -> ApiPaths.V2_PATH;
            default -> ApiPaths.LATEST_PATH;
        };
    }
    
    /**
     * 버전이 지원되는지 확인
     */
    public static boolean isSupportedVersion(String version) {
        for (String supportedVersion : ApiVersions.SUPPORTED_VERSIONS) {
            if (supportedVersion.equals(version)) {
                return true;
            }
        }
        return false;
    }
}
