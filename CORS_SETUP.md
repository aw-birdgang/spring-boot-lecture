# CORS 설정 가이드

## 개요

Cross-Origin Resource Sharing (CORS)는 웹 브라우저에서 다른 도메인으로의 요청을 제한하는 보안 정책입니다. 이 프로젝트에서는 환경별로 적절한 CORS 설정을 제공합니다.

## 설정 구조

### 1. 공통 CORS 설정 (`common` 모듈)

- **WebConfig**: 기본 CORS 매핑 설정
- **CorsConfig**: 환경별 CORS 설정

### 2. API별 Security CORS 설정

각 API 모듈의 `SecurityConfig`에서 Spring Security와 통합된 CORS 설정을 제공합니다.

## 환경별 설정

### 개발 환경 (dev)

```java
// 모든 도메인 허용
configuration.setAllowedOriginPatterns(Arrays.asList("*"));
```

**설정 파일**: `application-dev.properties`
```properties
# CORS 설정 (개발 환경)
cors.allowed-origins=
```

### 운영 환경 (prod)

```java
// 특정 도메인만 허용
configuration.setAllowedOrigins(Arrays.asList(
    "https://yourdomain.com",
    "https://www.yourdomain.com"
));
```

**설정 파일**: `application-prod.properties`
```properties
# CORS 설정 (운영 환경)
cors.allowed-origins=https://yourdomain.com,https://www.yourdomain.com
```

## 허용된 HTTP 메서드

- GET
- POST
- PUT
- DELETE
- OPTIONS
- PATCH

## 허용된 헤더

- 모든 헤더 허용 (`*`)

## 인증 정보 포함

- `allowCredentials = true`: 쿠키, 인증 헤더 포함 허용

## 캐시 설정

- `maxAge = 3600`: 1시간 동안 CORS 설정 캐시

## 사용 예시

### 프론트엔드에서 API 호출

```javascript
// JavaScript 예시
fetch('http://localhost:8081/mobile/api/users', {
    method: 'GET',
    credentials: 'include', // 쿠키 포함
    headers: {
        'Content-Type': 'application/json'
    }
})
.then(response => response.json())
.then(data => console.log(data));
```

### Axios 예시

```javascript
// Axios 설정
const api = axios.create({
    baseURL: 'http://localhost:8081',
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json'
    }
});

// API 호출
api.get('/mobile/api/users')
    .then(response => console.log(response.data));
```

## 문제 해결

### 1. CORS 오류 발생 시

**오류 메시지**: `Access to fetch at 'http://localhost:8081/mobile/api/users' from origin 'http://localhost:3000' has been blocked by CORS policy`

**해결 방법**:
1. API 서버가 실행 중인지 확인
2. 올바른 포트로 요청하는지 확인
3. 개발 환경에서는 모든 도메인이 허용되므로 설정 확인

### 2. 인증 관련 CORS 오류

**오류 메시지**: `The value of the 'Access-Control-Allow-Credentials' header in the response is '' which must be 'true' when the request's credentials mode is 'include'`

**해결 방법**:
1. `allowCredentials = true` 설정 확인
2. 프론트엔드에서 `credentials: 'include'` 설정 확인

### 3. 운영 환경에서 특정 도메인만 허용

**설정 방법**:
```properties
# application-prod.properties
cors.allowed-origins=https://yourdomain.com,https://www.yourdomain.com
```

## 보안 고려사항

### 개발 환경
- 모든 도메인 허용 (`*`)
- 빠른 개발을 위한 편의성 제공

### 운영 환경
- 특정 도메인만 허용
- 보안 강화를 위한 제한적 접근

### 권장사항
1. 운영 환경에서는 반드시 특정 도메인만 허용
2. HTTPS 사용 권장
3. 불필요한 HTTP 메서드 제한
4. 정기적인 CORS 설정 검토

## 테스트

### CORS 설정 테스트

```bash
# curl을 사용한 CORS 테스트
curl -H "Origin: http://localhost:3000" \
     -H "Access-Control-Request-Method: GET" \
     -H "Access-Control-Request-Headers: Content-Type" \
     -X OPTIONS \
     http://localhost:8081/mobile/api/users
```

### 예상 응답 헤더

```
Access-Control-Allow-Origin: http://localhost:3000
Access-Control-Allow-Methods: GET,POST,PUT,DELETE,OPTIONS,PATCH
Access-Control-Allow-Headers: Content-Type
Access-Control-Allow-Credentials: true
Access-Control-Max-Age: 3600
```

## 추가 설정

### 커스텀 CORS 설정

특정 엔드포인트에 대해 다른 CORS 설정을 적용하려면:

```java
@CrossOrigin(origins = {"https://specific-domain.com"})
@RestController
public class CustomController {
    // 특정 컨트롤러에만 적용되는 CORS 설정
}
```

### 전역 CORS 설정 재정의

```java
@Configuration
public class CustomCorsConfig {
    
    @Bean
    public CorsConfigurationSource customCorsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 커스텀 설정
        return source;
    }
}
```

## 참고 자료

- [Spring CORS Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-cors)
- [MDN CORS Guide](https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS)
- [Spring Security CORS](https://docs.spring.io/spring-security/site/docs/current/reference/html5/#cors)
