package birdgang.spring.lecture.common.interceptor;

import birdgang.spring.lecture.common.util.LogUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class LoggingInterceptor implements HandlerInterceptor {
    
    private static final Logger logger = LogUtils.getLogger(LoggingInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        String requestId = LogUtils.setRequestId();
        
        // 요청 정보 로깅
        logger.info("Request started - ID: {}, Method: {}, URI: {}, User-Agent: {}", 
                   requestId, 
                   request.getMethod(), 
                   request.getRequestURI(),
                   request.getHeader("User-Agent"));
        
        // 요청 시작 시간을 속성으로 저장
        request.setAttribute("startTime", startTime);
        request.setAttribute("requestId", requestId);
        
        return true;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        String requestId = (String) request.getAttribute("requestId");
        long executionTime = System.currentTimeMillis() - startTime;
        
        // 응답 정보 로깅
        logger.info("Request completed - ID: {}, Status: {}, ExecutionTime: {}ms", 
                   requestId, 
                   response.getStatus(), 
                   executionTime);
        
        // 성능 로깅
        LogUtils.Performance.logExecutionTime(request.getRequestURI(), startTime);
        
        // 컨텍스트 정리
        LogUtils.clearContext();
    }
}
