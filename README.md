# Spring Boot Lecture Project

Spring Boot 3.0.2를 활용한 멀티 모듈 API 프로젝트입니다. 모바일, 웹, 관리자 API를 분리하여 관리하는 마이크로서비스 아키텍처를 구현했습니다.

## 🚀 주요 기능

### ✅ 완료된 기능
- **멀티 모듈 아키텍처**: Mobile, Web, Admin API 분리
- **보안 강화**: Spring Security 적용, 각 API별 보안 설정
- **예외 처리**: 커스텀 예외 클래스 및 통합 예외 처리
- **데이터베이스**: JPA/Hibernate, H2 인메모리 데이터베이스
- **API 문서화**: Swagger/OpenAPI 3.0 (SpringDoc)
- **캐싱**: Redis 및 Caffeine 캐시 전략
- **로깅**: 구조화된 로깅 시스템 (Logback)
- **테스트**: JUnit 5 + Mockito 기반 테스트
- **환경별 설정**: dev/staging/prod 프로필 분리
- **모니터링**: Spring Boot Actuator

### 🔄 향후 구현 예정 기능
- API 버전 관리 시스템
- 성능 모니터링 및 메트릭 수집
- 게시글 관리 시스템
- 사용자 권한 관리 시스템

## 🏗️ 프로젝트 구조

```
spring-boot-lecture/
├── 📁 common/                    # 공통 모듈
│   ├── config/                   # 공통 설정 (보안, 캐시, API 등)
│   ├── controller/               # 공통 컨트롤러
│   ├── exception/                # 예외 처리 (BusinessException, ResourceNotFoundException 등)
│   ├── interceptor/              # 로깅 인터셉터
│   ├── util/                     # 유틸리티 (로깅, 캐시 키 생성 등)
│   └── service/                  # 공통 서비스
├── 📁 database/                  # 데이터베이스 모듈
│   ├── entity/                   # 엔티티 (User, Post)
│   └── repository/               # 리포지토리
├── 📁 shared-lib/                # 공유 라이브러리
│   ├── dto/                      # DTO (UserDto, PostDto)
│   └── util/                     # 매퍼 클래스 (UserMapper, PostMapper)
├── 📁 projects/                  # API 프로젝트들
│   ├── mobile-api/               # 모바일 API (포트: 8081)
│   │   ├── controller/           # MobileUserController
│   │   ├── service/              # 사용자 서비스
│   │   └── config/               # 모바일 API 설정
│   ├── web-api/                  # 웹 API (포트: 8082)
│   │   ├── controller/           # WebUserController
│   │   ├── service/              # 사용자 서비스
│   │   └── config/               # 웹 API 설정
│   └── admin-api/                # 관리자 API (포트: 8083)
│       ├── controller/           # AdminUserController
│       ├── service/              # 사용자 서비스
│       └── config/               # 관리자 API 설정
├── 📁 scripts/                   # 실행 스크립트
├── 📁 logs/                      # 로그 파일
├── 📁 data/                      # 데이터 파일
└── 📁 database/                  # 데이터베이스 관련 파일
```

## 🛠️ 기술 스택

### Backend
- **Java 17** - 최신 LTS 버전
- **Spring Boot 3.0.2** - 최신 Spring Boot 버전
- **Spring Security** - 보안 프레임워크
- **Spring Data JPA** - 데이터 접근 계층
- **H2 Database** - 인메모리 데이터베이스
- **Gradle 7.0+** - 빌드 도구

### 개발 도구 및 라이브러리
- **Swagger/OpenAPI 3.0** - API 문서화 (SpringDoc 2.1.0)
- **Redis** - 분산 캐시 및 세션 저장소
- **Caffeine Cache** - 로컬 캐시
- **SLF4J + Logback** - 로깅 프레임워크
- **JUnit 5 + Mockito** - 테스트 프레임워크
- **Spring Boot Actuator** - 모니터링 및 관리

## 🚀 빠른 시작

### 1. 프로젝트 클론
```bash
git clone https://github.com/birdgang/spring-boot-lecture.git
cd spring-boot-lecture
```

### 2. 모든 API 시작 (권장)
```bash
# 실행 권한 부여
chmod +x start-all-apis.sh
chmod +x stop-all-apis.sh

# 모든 API 시작
./start-all-apis.sh
```

### 3. 개별 API 시작
```bash
# Mobile API 시작
./gradlew :projects:mobile-api:bootRun

# Web API 시작
./gradlew :projects:web-api:bootRun

# Admin API 시작
./gradlew :projects:admin-api:bootRun
```

### 4. API 접근
- **Mobile API**: http://localhost:8081
- **Web API**: http://localhost:8082
- **Admin API**: http://localhost:8083

### 5. API 문서 확인 (Swagger UI)
- **Mobile API**: http://localhost:8081/swagger-ui.html
- **Web API**: http://localhost:8082/swagger-ui.html
- **Admin API**: http://localhost:8083/swagger-ui.html

> 💡 **Swagger UI 접근 방법**: 각 API 서버가 실행된 후, 브라우저에서 위 URL에 접근하면 API 문서를 확인할 수 있습니다.

### 6. H2 데이터베이스 콘솔
- **Mobile API**: http://localhost:8081/h2-console
- **Web API**: http://localhost:8082/h2-console
- **Admin API**: http://localhost:8083/h2-console

### 7. API 테스트 및 문서화
- **Swagger UI**: 각 API별 실시간 API 문서 및 테스트 도구
- **OpenAPI JSON**: `/v3/api-docs` 엔드포인트로 OpenAPI 스펙 다운로드
- **API 테스트**: Swagger UI에서 직접 API 호출 및 응답 확인

## 📋 API 엔드포인트

> 📚 **API 문서**: 모든 API 엔드포인트는 Swagger UI에서 실시간으로 확인하고 테스트할 수 있습니다.

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
- **Java 17+** - OpenJDK 또는 Oracle JDK
- **Gradle 7.0+** - 프로젝트에 포함된 Gradle Wrapper 사용 권장
- **IDE** - IntelliJ IDEA, Eclipse, VS Code 등

### 환경별 실행
```bash
# 개발 환경
./gradlew :projects:mobile-api:bootRunDev
./gradlew :projects:web-api:bootRunDev
./gradlew :projects:admin-api:bootRunDev

# 스테이징 환경
./gradlew :projects:mobile-api:bootRunStaging
./gradlew :projects:web-api:bootRunStaging
./gradlew :projects:admin-api:bootRunStaging

# 운영 환경
./gradlew :projects:mobile-api:bootRunProd
./gradlew :projects:web-api:bootRunProd
./gradlew :projects:admin-api:bootRunProd
```

### 환경 변수 설정
프로젝트 루트에 있는 환경별 설정 파일을 참조하세요:
- `env.dev` - 개발 환경 설정
- `env.staging` - 스테이징 환경 설정
- `env.prod` - 운영 환경 설정

## 🧪 테스트

### 테스트 실행
```bash
# 전체 테스트
./gradlew test

# 특정 모듈 테스트
./gradlew :projects:mobile-api:test
./gradlew :projects:web-api:test
./gradlew :projects:admin-api:test

# 공통 모듈 테스트
./gradlew :common:test
./gradlew :database:test
./gradlew :shared-lib:test

# 테스트 커버리지
./gradlew test jacocoTestReport
```

### 테스트 구조
- **단위 테스트**: 서비스 레이어 테스트
- **통합 테스트**: 컨트롤러 레이어 테스트
- **테스트 환경**: H2 인메모리 데이터베이스 사용
- **테스트 프레임워크**: JUnit 5 + Mockito

## 🔒 보안

### 구현된 보안 기능
- **Spring Security**: 각 API별 보안 설정
- **커스텀 예외**: 보안 관련 예외 처리
- **입력 검증**: Bean Validation 적용
- **세션 관리**: Redis 기반 세션 저장소

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
- **로그 설정**: `logback-spring.xml`에서 상세 설정

### Actuator
- **Health Check**: `/actuator/health`
- **Info**: `/actuator/info`
- **Metrics**: `/actuator/metrics`
- **Cache**: `/actuator/caches`
- **Environment**: `/actuator/env`

## 🚀 배포

### 빌드
```bash
# 전체 프로젝트 빌드
./gradlew clean build

# 특정 모듈 빌드
./gradlew :projects:mobile-api:build
./gradlew :projects:web-api:build
./gradlew :projects:admin-api:build
```

### JAR 실행
```bash
# 개발 환경
java -jar projects/mobile-api/build/libs/mobile-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev

# 운영 환경
java -jar projects/mobile-api/build/libs/mobile-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### Docker 실행 (향후 지원 예정)
```bash
# Docker 이미지 빌드
docker build -t spring-boot-lecture .

# Docker 컨테이너 실행
docker run -p 8081:8081 spring-boot-lecture
```

## 📚 문서

- [개발 가이드](DEVELOPMENT_GUIDE.md) - 상세한 개발 가이드
- [빌드 및 실행 가이드](BUILD_AND_RUN_GUIDE.md) - 빌드 및 실행 방법
- [환경 설정 가이드](ENVIRONMENT_SETUP_GUIDE.md) - 환경별 설정 방법
- [CORS 설정 가이드](CORS_SETUP.md) - CORS 설정 방법
- [Spring Boot 핵심 스펙](SPRING_BOOT_CORE_SPEC.md) - 핵심 기능 스펙
- [프로젝트 구조](README_NEW_STRUCTURE.md) - 상세한 프로젝트 구조

## 🔍 Swagger UI 사용법

### Swagger UI 접근
각 API 서버의 Swagger UI에 접근하여 API를 실시간으로 문서화하고 테스트할 수 있습니다.

#### 📱 Mobile API Swagger UI
- **URL**: http://localhost:8081/swagger-ui.html
- **API 경로**: `/mobile/api/**`
- **주요 기능**: 사용자 등록, 프로필 관리, 중복 확인

#### 🌐 Web API Swagger UI
- **URL**: http://localhost:8082/swagger-ui.html
- **API 경로**: `/web/api/**`
- **주요 기능**: 사용자 CRUD, 전체 사용자 조회

#### 🔧 Admin API Swagger UI
- **URL**: http://localhost:8083/swagger-ui.html
- **API 경로**: `/admin/api/**`
- **주요 기능**: 사용자 관리, 검색, 활성화/비활성화

### Swagger UI 주요 기능
- **API 문서 자동 생성**: 컨트롤러 어노테이션 기반 자동 문서화
- **실시간 API 테스트**: 브라우저에서 직접 API 호출 및 테스트
- **요청/응답 스키마**: DTO 클래스 기반 자동 스키마 생성
- **API 버전 관리**: OpenAPI 3.0 스펙 준수
- **보안 설정**: Spring Security 연동 인증 정보 표시

### Swagger 설정 정보
- **라이브러리**: SpringDoc OpenAPI 2.1.0
- **설정 파일**: 각 API 모듈의 `@Configuration` 클래스
- **API 정보**: 프로젝트 메타데이터 자동 표시
- **환경별 설정**: dev/staging/prod 환경에 따른 동적 설정

### OpenAPI 스펙 엔드포인트
각 API 서버에서 OpenAPI 3.0 스펙을 JSON 형태로 다운로드할 수 있습니다:
- **Mobile API**: http://localhost:8081/v3/api-docs
- **Web API**: http://localhost:8082/v3/api-docs
- **Admin API**: http://localhost:8083/v3/api-docs

### Swagger UI 커스터마이징
- **테마**: 기본 Swagger UI 테마 사용
- **API 그룹**: 컨트롤러별 자동 그룹핑
- **보안**: Spring Security 연동으로 인증된 API 표시
- **로컬라이제이션**: 한국어 환경 지원 (향후 추가 예정)

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

## 🔄 최근 업데이트

- **2024년**: Spring Boot 3.0.2 업그레이드
- **2024년**: Java 17 지원
- **2024년**: 멀티 모듈 아키텍처 구현
- **2024년**: 환경별 설정 분리
- **2024년**: 통합 테스트 환경 구축

---

**Spring Boot Lecture Project** - 멀티 모듈 API 아키텍처의 완벽한 구현

> 💡 **팁**: 프로젝트를 처음 시작하시는 분은 `BUILD_AND_RUN_GUIDE.md`를 먼저 읽어보세요!



