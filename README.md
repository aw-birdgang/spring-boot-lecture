# Spring Boot Lecture Project

Spring Boot를 활용한 멀티 모듈 API 프로젝트입니다. 모바일, 웹, 관리자 API를 분리하여 관리하는 마이크로서비스 아키텍처를 구현했습니다.

## 🚀 주요 기능

### ✅ 완료된 기능
- **멀티 모듈 아키텍처**: Mobile, Web, Admin API 분리
- **보안 강화**: 비밀번호 암호화, Spring Security 적용
- **예외 처리**: 커스텀 예외 클래스 및 통합 예외 처리
- **데이터베이스**: JPA/Hibernate, H2 데이터베이스
- **API 문서화**: Swagger/OpenAPI 3.0
- **캐싱**: Caffeine 캐시 전략
- **로깅**: 구조화된 로깅 시스템
- **테스트**: 단위 테스트 및 통합 테스트
- **환경별 설정**: dev/prod/test 프로필 분리

### 🔄 진행 중인 기능
- API 버전 관리 시스템
- 성능 모니터링
- 메트릭 수집

## 🏗️ 프로젝트 구조

```
spring-boot-lecture/
├── common/                    # 공통 모듈
│   ├── config/               # 공통 설정 (보안, 캐시, API 등)
│   ├── controller/           # 공통 컨트롤러
│   ├── exception/            # 예외 처리 (BusinessException, ResourceNotFoundException 등)
│   ├── interceptor/          # 로깅 인터셉터
│   ├── util/                 # 유틸리티 (로깅, 캐시 키 생성 등)
│   └── service/              # 공통 서비스
├── database/                 # 데이터베이스 모듈
│   ├── entity/               # 엔티티 (User, Post)
│   └── repository/           # 리포지토리
├── shared-lib/               # 공유 라이브러리
│   ├── dto/                  # DTO (UserDto, PostDto)
│   └── util/                 # 매퍼 클래스 (UserMapper, PostMapper)
└── projects/                 # API 프로젝트들
    ├── mobile-api/           # 모바일 API (포트: 8081)
    ├── web-api/              # 웹 API (포트: 8082)
    └── admin-api/            # 관리자 API (포트: 8083)
```

## 🛠️ 기술 스택

### Backend
- **Java 17**
- **Spring Boot 3.0.2**
- **Spring Security**
- **Spring Data JPA**
- **H2 Database**
- **Gradle**

### 개발 도구
- **Swagger/OpenAPI 3.0**
- **Caffeine Cache**
- **SLF4J + Logback**
- **JUnit 5 + Mockito**

## 🚀 빠른 시작

### 1. 프로젝트 클론
```bash
git clone https://github.com/birdgang/spring-boot-lecture.git
cd spring-boot-lecture
```

### 2. 모든 API 시작
```bash
./start-all-apis.sh
```

### 3. API 접근
- **Mobile API**: http://localhost:8081
- **Web API**: http://localhost:8082
- **Admin API**: http://localhost:8083

### 4. API 문서 확인
- **Mobile API**: http://localhost:8081/swagger-ui.html
- **Web API**: http://localhost:8082/swagger-ui.html
- **Admin API**: http://localhost:8083/swagger-ui.html

## 📋 API 엔드포인트

### 사용자 관리 API

#### Mobile API (`/mobile/api/users`)
```
POST   /register              # 사용자 등록
GET    /profile/{id}          # 프로필 조회
PUT    /profile/{id}          # 프로필 수정
GET    /check/username/{name} # 사용자명 중복 확인
GET    /check/email/{email}   # 이메일 중복 확인
```

#### Web API (`/web/api/users`)
```
POST   /                      # 사용자 생성
GET    /                      # 전체 사용자 조회
GET    /{id}                  # 사용자 조회
GET    /username/{username}   # 사용자명으로 조회
PUT    /{id}                  # 사용자 수정
DELETE /{id}                  # 사용자 삭제
```

#### Admin API (`/admin/api/users`)
```
GET    /                      # 사용자 목록 (페이징)
GET    /{id}                  # 사용자 상세 조회
PUT    /{id}                  # 사용자 수정
DELETE /{id}                  # 사용자 삭제
GET    /search                # 사용자 검색
POST   /{id}/activate         # 사용자 활성화
POST   /{id}/deactivate       # 사용자 비활성화
```

### 게시글 관리 API (향후 구현 예정)
```
GET    /posts                 # 게시글 목록
GET    /posts/{id}            # 게시글 조회
POST   /posts                 # 게시글 생성
PUT    /posts/{id}            # 게시글 수정
DELETE /posts/{id}            # 게시글 삭제
```

## 🔧 개발 환경 설정

### 필수 요구사항
- Java 17+
- Gradle 7.0+
- IDE (IntelliJ IDEA 권장)

### 환경별 실행
```bash
# 개발 환경
./gradlew bootRun --args='--spring.profiles.active=dev'

# 테스트 환경
./gradlew test

# 운영 환경
./gradlew bootRun --args='--spring.profiles.active=prod'
```

## 🧪 테스트

### 테스트 실행
```bash
# 전체 테스트
./gradlew test

# 특정 모듈 테스트
./gradlew :projects:mobile-api:test
./gradlew :projects:web-api:test
./gradlew :projects:admin-api:test

# 테스트 커버리지
./gradlew test jacocoTestReport
```

### 테스트 구조
- **단위 테스트**: 서비스 레이어 테스트
- **통합 테스트**: 컨트롤러 레이어 테스트
- **테스트 환경**: H2 인메모리 데이터베이스 사용

## 🔒 보안

### 구현된 보안 기능
- **비밀번호 암호화**: BCrypt 사용
- **Spring Security**: 각 API별 보안 설정
- **커스텀 예외**: 보안 관련 예외 처리
- **입력 검증**: Bean Validation 적용

### 보안 설정
- **Mobile API**: 기본 인증 (개발용)
- **Web API**: 폼 로그인 + 세션 관리
- **Admin API**: 강화된 인증 + 권한 관리

## 📊 모니터링

### 로깅
- **구조화된 로깅**: MDC를 활용한 요청 추적
- **성능 로깅**: 실행 시간 측정
- **보안 로깅**: 인증/인가 이벤트 기록
- **비즈니스 로깅**: 주요 비즈니스 이벤트 기록

### Actuator
- **Health Check**: `/actuator/health`
- **Info**: `/actuator/info`
- **Metrics**: `/actuator/metrics`
- **Cache**: `/actuator/caches`

## 🚀 배포

### 빌드
```bash
# 전체 프로젝트 빌드
./gradlew clean build

# 특정 모듈 빌드
./gradlew :projects:mobile-api:build
```

### JAR 실행
```bash
# 개발 환경
java -jar projects/mobile-api/build/libs/mobile-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev

# 운영 환경
java -jar projects/mobile-api/build/libs/mobile-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## 📚 문서

- [개발 가이드](DEVELOPMENT_GUIDE.md) - 상세한 개발 가이드
- [API 문서](http://localhost:8081/swagger-ui.html) - Swagger UI
- [프로젝트 구조](README_NEW_STRUCTURE.md) - 상세한 프로젝트 구조

## 🤝 기여하기

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.

## 📞 문의

프로젝트에 대한 문의사항이 있으시면 이슈를 생성해 주세요.

---

**Spring Boot Lecture Project** - 멀티 모듈 API 아키텍처의 완벽한 구현



