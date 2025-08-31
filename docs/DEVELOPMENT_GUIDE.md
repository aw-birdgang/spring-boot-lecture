# Spring Boot Lecture - 개발 가이드

## 📋 목차

1. [프로젝트 구조](#프로젝트-구조)
2. [개발 환경 설정](#개발-환경-설정)
3. [API 설계 가이드](#api-설계-가이드)
4. [코딩 컨벤션](#코딩-컨벤션)
5. [테스트 가이드](#테스트-가이드)
6. [배포 가이드](#배포-가이드)

## 🏗️ 프로젝트 구조

```
spring-boot-lecture/
├── common/                    # 공통 모듈
│   ├── config/               # 공통 설정
│   ├── controller/           # 공통 컨트롤러
│   ├── exception/            # 예외 처리
│   ├── interceptor/          # 인터셉터
│   ├── util/                 # 유틸리티
│   └── service/              # 공통 서비스
├── database/                 # 데이터베이스 모듈
│   ├── entity/               # 엔티티
│   └── repository/           # 리포지토리
├── shared-lib/               # 공유 라이브러리
│   ├── dto/                  # DTO
│   └── util/                 # 공유 유틸리티
└── projects/                 # API 프로젝트들
    ├── mobile-api/           # 모바일 API
    ├── web-api/              # 웹 API
    └── admin-api/            # 관리자 API
```

## ⚙️ 개발 환경 설정

### 필수 요구사항

- Java 17+
- Gradle 7.0+
- IDE (IntelliJ IDEA 권장)

### 환경별 설정

#### 개발 환경
```bash
# 기본 설정 (dev 프로필)
./gradlew bootRun

# 특정 프로필 지정
./gradlew bootRun --args='--spring.profiles.active=dev'
```

#### 테스트 환경
```bash
# 테스트 실행
./gradlew test

# 특정 테스트만 실행
./gradlew test --tests *UserServiceTest
```

#### 운영 환경
```bash
# 운영 환경 실행
./gradlew bootRun --args='--spring.profiles.active=prod'
```

## 🎯 API 설계 가이드

### API 버전 관리

#### 버전 지정 방법
```java
@ApiVersion("v1")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    // API 구현
}
```

#### 지원하는 버전
- `v1`: 현재 최신 버전
- `v2`: 향후 확장 버전

### REST API 설계 원칙

#### URL 설계
```
GET    /api/v1/users          # 사용자 목록 조회
GET    /api/v1/users/{id}     # 특정 사용자 조회
POST   /api/v1/users          # 사용자 생성
PUT    /api/v1/users/{id}     # 사용자 수정
DELETE /api/v1/users/{id}     # 사용자 삭제
```

#### HTTP 상태 코드
- `200 OK`: 성공
- `201 Created`: 생성 성공
- `400 Bad Request`: 잘못된 요청
- `401 Unauthorized`: 인증 실패
- `403 Forbidden`: 권한 없음
- `404 Not Found`: 리소스 없음
- `409 Conflict`: 충돌
- `500 Internal Server Error`: 서버 오류

### 응답 형식

#### 성공 응답
```json
{
  "id": 1,
  "username": "testuser",
  "email": "test@example.com",
  "fullName": "Test User",
  "active": true,
  "createdAt": "2024-01-01T00:00:00",
  "updatedAt": "2024-01-01T00:00:00"
}
```

#### 에러 응답
```json
{
  "status": 400,
  "errorCode": "VALIDATION_ERROR",
  "message": "입력값 검증 실패",
  "timestamp": "2024-01-01T00:00:00",
  "details": {
    "username": "사용자명은 필수입니다"
  }
}
```

## 📝 코딩 컨벤션

### Java 코딩 스타일

#### 클래스 명명
- 컨트롤러: `*Controller`
- 서비스: `*Service`, `*ServiceImpl`
- 리포지토리: `*Repository`
- 엔티티: `*` (도메인명)
- DTO: `*Dto`, `*Request`, `*Response`

#### 메서드 명명
```java
// 조회
findById(Long id)
findAll()
findByUsername(String username)

// 생성
createUser(UserDto.CreateRequest request)
save(User user)

// 수정
updateUser(Long id, UserDto.UpdateRequest request)
update(User user)

// 삭제
deleteUser(Long id)
deleteById(Long id)
```

### 의존성 주입

#### 생성자 주입 사용 (권장)
```java
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
}
```

### 예외 처리

#### 커스텀 예외 사용
```java
// 비즈니스 예외
throw new BusinessException("사용자 생성에 실패했습니다");

// 리소스 없음
throw new ResourceNotFoundException("User", "id", id);

// 중복 리소스
throw new DuplicateResourceException("User", "username", username);
```

### 로깅

#### 구조화된 로깅 사용
```java
private static final Logger logger = LogUtils.getLogger(UserService.class);

// 정보 로깅
logger.info("사용자 생성 완료: {}", username);

// 에러 로깅
logger.error("사용자 생성 실패: {}", username, exception);

// 성능 로깅
LogUtils.Performance.logExecutionTime("createUser", startTime);
```

## 🧪 테스트 가이드

### 단위 테스트

#### 서비스 테스트 예시
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    @Test
    void createUser_Success() {
        // Given
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        
        // When
        UserDto.Response response = userService.createUser(request);
        
        // Then
        assertNotNull(response);
        assertEquals(expectedUsername, response.getUsername());
    }
}
```

### 통합 테스트

#### 컨트롤러 테스트 예시
```java
@SpringBootTest
@AutoConfigureTestDatabase
class UserControllerIntegrationTest {
    @Test
    void createUser_Success() throws Exception {
        // Given
        UserDto.CreateRequest request = new UserDto.CreateRequest();
        request.setUsername("testuser");
        
        // When & Then
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser"));
    }
}
```

### 테스트 실행

```bash
# 전체 테스트 실행
./gradlew test

# 특정 모듈 테스트 실행
./gradlew :projects:mobile-api:test

# 테스트 커버리지 확인
./gradlew test jacocoTestReport
```

## 🚀 배포 가이드

### 빌드

```bash
# 전체 프로젝트 빌드
./gradlew clean build

# 특정 모듈 빌드
./gradlew :projects:mobile-api:build
```

### JAR 파일 실행

```bash
# 개발 환경
java -jar projects/mobile-api/build/libs/mobile-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev

# 운영 환경
java -jar projects/mobile-api/build/libs/mobile-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### Docker 배포

```bash
# Docker 이미지 빌드
docker build -t spring-boot-lecture:latest .

# Docker 컨테이너 실행
docker run -p 8081:8081 -e SPRING_PROFILES_ACTIVE=prod spring-boot-lecture:latest
```

## 🔧 유용한 명령어

### 프로젝트 관리
```bash
# 의존성 확인
./gradlew dependencies

# 프로젝트 구조 확인
./gradlew projects

# 빌드 캐시 정리
./gradlew clean
```

### 개발 도구
```bash
# 모든 API 시작
./start-all-apis.sh

# 모든 API 종료
./stop-all-apis.sh

# 로그 확인
tail -f mobile-api.log
```

## 📚 추가 자료

- [Spring Boot 공식 문서](https://spring.io/projects/spring-boot)
- [Spring Security 가이드](https://spring.io/projects/spring-security)
- [JPA/Hibernate 문서](https://hibernate.org/orm/documentation/)
- [Gradle 사용법](https://gradle.org/guides/)

## 🤝 기여 가이드

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.
