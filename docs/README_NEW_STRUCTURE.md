# Spring Boot Lecture - 새로운 모듈 구조

Spring Boot 프로젝트를 여러 개의 독립적인 애플리케이션으로 분리하고, 공통 모듈과 데이터베이스 모듈을 재사용할 수 있도록 구조를 재구성했습니다.

## 🏗️ **새로운 프로젝트 구조**

```
spring-boot-lecture/
├── common/                    # 공통 모듈 (공유 라이브러리)
│   ├── config/               # 공통 설정
│   ├── exception/            # 예외 처리
│   └── service/              # 공통 서비스
├── database/                  # 데이터베이스 모듈 (공유 라이브러리)
│   ├── entity/               # JPA 엔티티
│   └── repository/           # 데이터 접근 계층
├── shared-lib/                # 공유 DTO 모듈 (공유 라이브러리)
│   └── dto/                  # 데이터 전송 객체
├── projects/                  # API 프로젝트들
│   ├── mobile-api/           # 모바일 API 애플리케이션
│   │   ├── controller/       # 모바일 전용 컨트롤러
│   │   ├── service/          # 모바일 전용 서비스
│   │   └── application.properties # 모바일 API 설정
│   ├── web-api/              # 웹 API 애플리케이션
│   │   ├── controller/       # 웹 전용 컨트롤러
│   │   ├── service/          # 웹 전용 서비스
│   │   └── application.properties # 웹 API 설정
│   └── admin-api/            # 관리자 API 애플리케이션
│       ├── controller/       # 관리자 전용 컨트롤러
│       ├── service/          # 관리자 전용 서비스
│       └── application.properties # 관리자 API 설정
├── build.gradle              # 루트 빌드 설정
└── settings.gradle           # 멀티 모듈 설정
```

## 📱 **Mobile API (포트: 8081)**

**특징:**
- 모바일 앱 전용 API
- 캐싱 최적화 (Caffeine)
- 간소화된 사용자 관리
- Actuator 모니터링

**주요 엔드포인트:**
- `POST /mobile/api/users/register` - 모바일 사용자 등록
- `GET /mobile/api/users/profile/{id}` - 프로필 조회
- `PUT /mobile/api/users/profile/{id}` - 프로필 수정

**의존성:**
- Common 모듈
- Database 모듈
- Shared Library 모듈
- Spring Boot Cache
- Spring Boot Actuator

## 🌐 **Web API (포트: 8082)**

**특징:**
- 웹 애플리케이션 전용 API
- Spring Security 통합
- Thymeleaf 템플릿 엔진
- Redis 캐싱

**주요 엔드포인트:**
- `POST /web/api/users` - 웹 사용자 생성
- `GET /web/api/users/{id}` - 사용자 조회
- `PUT /web/api/users/{id}` - 사용자 수정
- `DELETE /web/api/users/{id}` - 사용자 삭제

**의존성:**
- Common 모듈
- Database 모듈
- Shared Library 모듈
- Spring Security
- Thymeleaf
- Redis

## 🔧 **Admin API (포트: 8083)**

**특징:**
- 관리자 전용 API
- 고급 모니터링 (Actuator + Prometheus)
- Redis 기반 캐싱
- 사용자 관리 기능 확장

**주요 엔드포인트:**
- `GET /admin/api/users` - 사용자 목록 (페이징)
- `GET /admin/api/users/search` - 사용자 검색
- `POST /admin/api/users/{id}/activate` - 사용자 활성화
- `POST /admin/api/users/{id}/deactivate` - 사용자 비활성화

**의존성:**
- Common 모듈
- Database 모듈
- Shared Library 모듈
- Spring Security
- Spring Boot Actuator
- Redis

## 🗄️ **Database 모듈 (공유 라이브러리)**

**포함 내용:**
- User, Post 엔티티
- UserRepository, PostRepository
- JPA 설정 및 의존성

**사용 방법:**
```gradle
implementation project(':database')
```

## 📦 **Shared Library 모듈 (공유 라이브러리)**

**포함 내용:**
- UserDto, PostDto
- 공통 DTO 클래스들
- Validation 어노테이션

**사용 방법:**
```gradle
implementation project(':shared-lib')
```

## 🚀 **실행 방법**

### **전체 프로젝트 빌드**
```bash
./gradlew build
```

### **개별 애플리케이션 실행**

#### **Mobile API 실행**
```bash
./gradlew :projects:mobile-api:bootRun
# http://localhost:8081
```

#### **Web API 실행**
```bash
./gradlew :projects:web-api:bootRun
# http://localhost:8082
```

#### **Admin API 실행**
```bash
./gradlew :projects:admin-api:bootRun
# http://localhost:8083
```

### **테스트 실행**
```bash
# 전체 테스트
./gradlew test

# 특정 모듈 테스트
./gradlew :projects:mobile-api:test
./gradlew :projects:web-api:test
./gradlew :projects:admin-api:test
```

## 🔗 **API 문서 접근**

### **Mobile API**
- Swagger UI: http://localhost:8081/swagger-ui.html
- H2 Console: http://localhost:8081/h2-console

### **Web API**
- Swagger UI: http://localhost:8082/swagger-ui.html
- H2 Console: http://localhost:8082/h2-console

### **Admin API**
- Swagger UI: http://localhost:8083/swagger-ui.html
- H2 Console: http://localhost:8083/h2-console
- Actuator: http://localhost:8083/actuator

## 💡 **모듈 분리의 장점**

### **1. 관심사 분리**
- 각 API는 특정 클라이언트에 최적화
- 비즈니스 로직과 인프라 분리

### **2. 독립적 배포**
- 각 API를 독립적으로 배포 가능
- 서로 다른 스케일링 정책 적용

### **3. 기술 스택 최적화**
- Mobile API: 캐싱 최적화
- Web API: 보안 및 템플릿 엔진
- Admin API: 모니터링 및 관리 기능

### **4. 코드 재사용**
- Common, Database, Shared Library 모듈 공유
- 중복 코드 제거

### **5. 개발 팀 분리**
- 각 API를 다른 팀에서 개발 가능
- 독립적인 개발 주기

### **6. 프로젝트 구조화**
- API 프로젝트들을 `projects/` 디렉토리로 그룹화
- 더 명확한 프로젝트 구조
- 쉬운 프로젝트 관리

## 🔧 **개발 환경 설정**

### **필수 요구사항**
- Java 17
- Gradle 7.x+
- Redis (선택사항, 캐싱용)

### **IDE 설정**
- IntelliJ IDEA 또는 Eclipse
- Spring Boot DevTools 활성화
- 각 모듈을 독립적으로 실행 가능

## 📝 **향후 확장 계획**

### **추가 모듈 고려사항**
- **Notification Service**: 알림 서비스
- **File Storage Service**: 파일 저장 서비스
- **Analytics Service**: 분석 서비스
- **Payment Service**: 결제 서비스

### **마이크로서비스 전환**
- 각 API를 독립적인 마이크로서비스로 분리
- API Gateway 도입
- Service Discovery 구현

이 구조를 통해 Spring Boot의 모듈화와 마이크로서비스 아키텍처의 장점을 모두 활용할 수 있습니다! 🎯
