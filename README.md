# Spring Boot Lecture Project

Spring Boot 강의를 위한 예제 프로젝트입니다.

## 프로젝트 구조

```
spring-boot-lecture/
├── src/main/java/birdgang/spring/lecture/
│   ├── HelloApplication.java          # 메인 애플리케이션 클래스
│   ├── HelloController.java           # Hello API 컨트롤러
│   ├── HelloService.java              # Hello 서비스 인터페이스
│   ├── SimpleHelloService.java        # Hello 서비스 구현체
│   ├── OpenApiConfig.java             # Swagger UI 설정
│   ├── entity/                        # 엔티티 클래스들
│   │   ├── User.java                  # 사용자 엔티티
│   │   └── Post.java                  # 게시글 엔티티
│   ├── dto/                           # DTO 클래스들
│   │   ├── UserDto.java               # 사용자 관련 DTO
│   │   └── PostDto.java               # 게시글 관련 DTO
│   ├── repository/                    # 리포지토리 인터페이스들
│   │   ├── UserRepository.java        # 사용자 리포지토리
│   │   └── PostRepository.java        # 게시글 리포지토리
│   ├── service/                       # 서비스 인터페이스들
│   │   ├── UserService.java           # 사용자 서비스 인터페이스
│   │   └── PostService.java           # 게시글 서비스 인터페이스
│   ├── service/impl/                  # 서비스 구현체들
│   │   ├── UserServiceImpl.java       # 사용자 서비스 구현체
│   │   └── PostServiceImpl.java       # 게시글 서비스 구현체
│   ├── controller/                    # 컨트롤러 클래스들
│   │   ├── UserController.java        # 사용자 API 컨트롤러
│   │   └── PostController.java        # 게시글 API 컨트롤러
│   ├── exception/                     # 예외 처리 클래스들
│   │   └── GlobalExceptionHandler.java # 전역 예외 처리기
│   └── config/                        # 설정 클래스들
│       └── DataInitializer.java       # 데이터 초기화 클래스
├── src/main/resources/
│   └── application.properties         # 애플리케이션 설정
└── src/test/java/                     # 테스트 코드
    └── UserApiTest.java               # 사용자 API 테스트
```

## 주요 기능

### 1. Hello API
- 기본적인 인사말을 제공하는 API
- `/hello?name=Spring` 형태로 호출

### 2. User API (`/api/users`)
- **POST** `/api/users` - 사용자 생성
- **GET** `/api/users/{id}` - ID로 사용자 조회
- **GET** `/api/users/username/{username}` - 사용자명으로 사용자 조회
- **GET** `/api/users` - 전체 사용자 조회
- **PUT** `/api/users/{id}` - 사용자 정보 수정
- **DELETE** `/api/users/{id}` - 사용자 삭제
- **GET** `/api/users/check/username/{username}` - 사용자명 중복 확인
- **GET** `/api/users/check/email/{email}` - 이메일 중복 확인

### 3. Post API (`/api/posts`)
- **POST** `/api/posts?authorId={id}` - 게시글 생성
- **GET** `/api/posts/{id}` - ID로 게시글 조회
- **GET** `/api/posts` - 게시글 목록 조회 (페이징, 정렬 지원)
- **GET** `/api/posts/search?keyword={keyword}` - 키워드로 게시글 검색
- **GET** `/api/posts/author/{authorId}` - 특정 사용자의 게시글 조회
- **PUT** `/api/posts/{id}?authorId={id}` - 게시글 수정
- **DELETE** `/api/posts/{id}?authorId={id}` - 게시글 삭제

## 기술 스택

- **Spring Boot 3.0.2**
- **Java 17**
- **Spring Data JPA**
- **H2 Database** (인메모리)
- **Spring Validation**
- **Swagger UI** (SpringDoc OpenAPI)

## 실행 방법

### 1. 애플리케이션 실행
```bash
./gradlew bootRun
```

### 2. 테스트 실행
```bash
./gradlew test
```

### 3. 빌드
```bash
./gradlew build
```

## API 문서

애플리케이션 실행 후 다음 URL에서 Swagger UI를 통해 API 문서를 확인할 수 있습니다:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs

## 데이터베이스

- **H2 Console**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (비어있음)

## 샘플 데이터

애플리케이션 시작 시 자동으로 다음 샘플 데이터가 생성됩니다:

### 사용자
- `john_doe` (john@example.com)
- `jane_smith` (jane@example.com)
- `admin` (admin@example.com)

### 게시글
- Spring Boot 시작하기
- JPA와 Hibernate 이해하기
- RESTful API 설계 원칙
- Spring Security 기초

## API 사용 예시

### 사용자 생성
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "email": "newuser@example.com",
    "password": "password123",
    "fullName": "New User"
  }'
```

### 게시글 생성
```bash
curl -X POST "http://localhost:8080/api/posts?authorId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "새로운 게시글",
    "content": "게시글 내용입니다."
  }'
```

### 게시글 목록 조회 (페이징)
```bash
curl "http://localhost:8080/api/posts?page=0&size=5&sortBy=createdAt&direction=desc"
```

### 게시글 검색
```bash
curl "http://localhost:8080/api/posts/search?keyword=Spring&page=0&size=10"
```

## 주의사항

1. **비밀번호 암호화**: 현재 구현에서는 비밀번호가 평문으로 저장됩니다. 실제 프로덕션에서는 BCrypt 등의 암호화를 적용해야 합니다.

2. **인증/인가**: 현재 구현에서는 사용자 인증이 없습니다. 실제 프로덕션에서는 Spring Security 등을 사용하여 인증/인가를 구현해야 합니다.

3. **데이터 검증**: 입력값에 대한 기본적인 검증은 구현되어 있지만, 비즈니스 로직에 따른 추가 검증이 필요할 수 있습니다.

## 라이센스

이 프로젝트는 교육 목적으로 제작되었습니다.



