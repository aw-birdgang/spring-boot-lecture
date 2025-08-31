# Spring Boot Lecture Project

Spring Boot 강의를 위한 멀티 모듈 프로젝트입니다.

## 프로젝트 구조

```
spring-boot-lecture/
├── common/                    # 공통 모듈
│   ├── src/main/java/
│   │   └── birdgang/spring/lecture/common/
│   │       ├── config/        # 공통 설정
│   │       ├── controller/    # 공통 컨트롤러
│   │       ├── exception/     # 예외 처리
│   │       └── service/       # 공통 서비스
│   └── build.gradle
├── api/                       # API 모듈 (DB 사용)
│   ├── src/main/java/
│   │   └── birdgang/spring/lecture/api/
│   │       ├── config/        # API 설정
│   │       ├── controller/    # API 컨트롤러
│   │       ├── dto/          # 데이터 전송 객체
│   │       ├── entity/       # JPA 엔티티
│   │       ├── repository/   # 데이터 접근 계층
│   │       └── service/      # 비즈니스 로직
│   ├── src/main/resources/
│   │   └── application.properties
│   └── build.gradle
├── build.gradle              # 루트 빌드 설정
└── settings.gradle           # 멀티 모듈 설정
```

## 모듈 설명

### Common 모듈
- **목적**: 공통 기능 제공
- **주요 기능**:
  - Hello API (인사말 서비스)
  - 공통 예외 처리
  - OpenAPI 설정
  - 공통 유틸리티

### API 모듈
- **목적**: 데이터베이스를 사용하는 비즈니스 로직
- **주요 기능**:
  - 사용자 관리 (User CRUD)
  - 게시글 관리 (Post CRUD)
  - JPA/Hibernate 기반 데이터 접근
  - RESTful API 제공

## 기술 스택

- **Spring Boot 3.0.2**
- **Java 17**
- **Spring Data JPA**
- **H2 Database** (인메모리)
- **SpringDoc OpenAPI** (Swagger UI)
- **Gradle** (멀티 모듈)

## 빌드 및 실행

### 전체 프로젝트 빌드
```bash
./gradlew build
```

### API 모듈 실행
```bash
./gradlew :api:bootRun
```

### 테스트 실행
```bash
# 전체 테스트
./gradlew test

# 특정 모듈 테스트
./gradlew :common:test
./gradlew :api:test
```

## API 엔드포인트

### Hello API (Common 모듈)
- `GET /hello?name={name}` - 인사말 조회

### User API (API 모듈)
- `POST /api/users` - 사용자 생성
- `GET /api/users/{id}` - 사용자 조회
- `GET /api/users/username/{username}` - 사용자명으로 조회
- `GET /api/users` - 전체 사용자 조회
- `PUT /api/users/{id}` - 사용자 정보 수정
- `DELETE /api/users/{id}` - 사용자 삭제
- `GET /api/users/check/username/{username}` - 사용자명 중복 확인
- `GET /api/users/check/email/{email}` - 이메일 중복 확인

### Post API (API 모듈)
- `POST /api/posts?authorId={id}` - 게시글 생성
- `GET /api/posts/{id}` - 게시글 조회
- `GET /api/posts` - 게시글 목록 조회 (페이징)
- `GET /api/posts/search?keyword={keyword}` - 게시글 검색
- `GET /api/posts/author/{authorId}` - 작성자별 게시글 조회
- `PUT /api/posts/{id}?authorId={id}` - 게시글 수정
- `DELETE /api/posts/{id}?authorId={id}` - 게시글 삭제

## 개발 도구

### Swagger UI
- URL: http://localhost:8080/swagger-ui.html
- API 문서 자동 생성 및 테스트 가능

### H2 Console
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (비어있음)

## 모듈 분리의 장점

1. **관심사 분리**: 공통 기능과 비즈니스 로직을 명확히 분리
2. **재사용성**: Common 모듈을 다른 프로젝트에서도 재사용 가능
3. **유지보수성**: 각 모듈의 독립적인 개발 및 배포 가능
4. **테스트 용이성**: 모듈별 독립적인 테스트 가능
5. **확장성**: 새로운 모듈 추가 시 기존 모듈에 영향 없음



