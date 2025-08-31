# Spring Boot 프로젝트 코어 스펙 가이드

## 1. 코어 스펙 (권장 기본값)

### 🚀 핵심 기술 스택

| 카테고리 | 기술 | 버전/설명 |
|---------|------|-----------|
| **JDK** | Java | **Java 21 LTS** (최신 LTS 버전) |
| **프레임워크** | Spring Boot | **3.x** (AOT/Native, 관찰성 강화, Jakarta 전환) |
| **빌드 도구** | Gradle | **Kotlin DSL** (더 간결하고 타입 안전한 빌드 스크립트) |
| **데이터베이스** | PostgreSQL | 운영/기능/생태계 균형이 좋음 |
| **JPA 구현체** | Hibernate + Spring Data JPA | 표준 JPA + Spring의 편의 기능 |
| **커넥션 풀** | HikariCP | Spring Boot 기본, 성능 우수 |
| **캐시** | Redis | Lettuce 클라이언트 사용 |
| **메시징** | Kafka | 대규모 이벤트 처리 |
| **메시징** | RabbitMQ | 단순 워크큐 처리 |
| **API 스타일** | REST | 필요 시 gRPC/GraphQL 혼용 |
| **보안** | Spring Security + OAuth2 Resource Server | JWT 기반 인증/인가 |
| **DB 마이그레이션** | Flyway | 또는 Liquibase |
| **관찰성** | Actuator + Micrometer + Prometheus | + OpenTelemetry(Trace) |
| **테스트** | JUnit5 + AssertJ + Mockito | + Testcontainers |
| **구성관리** | Spring Profiles + 환경변수 | 필요 시 Spring Cloud Config |
| **코드 생성/매핑** | MapStruct | DTO↔Entity 매핑 |
| **코드 생성/매핑** | Lombok | Optional (선택사항) |

---

## 2. 이상적인 "번들 조합" (용도별 패키지)

### 🎯 A. 웹 API 서비스 (모놀리식/도메인 분리 지향)

#### 필수 의존성
```gradle
// Spring Boot Starters
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.springframework.boot:spring-boot-starter-validation'
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
implementation 'org.springframework.boot:spring-boot-starter-security'
implementation 'org.springframework.boot:spring-boot-starter-oauth2-resource-server'
implementation 'org.springframework.boot:spring-boot-starter-cache'
implementation 'org.springframework.boot:spring-boot-starter-actuator'

// 데이터베이스
implementation 'org.postgresql:postgresql'
implementation 'org.flywaydb:flyway-core'

// 캐시
implementation 'org.springframework.data:spring-data-redis'

// 관찰성
implementation 'io.micrometer:micrometer-registry-prometheus'

// API 문서
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui'

// 매핑
implementation 'org.mapstruct:mapstruct'
annotationProcessor 'org.mapstruct:mapstruct-processor'
```

#### 선택적 의존성
```gradle
// QueryDSL 또는 jOOQ (둘 중 하나만)
implementation 'com.querydsl:querydsl-jpa:5.0.0:jakarta'
annotationProcessor 'com.querydsl:querydsl-apt:5.0.0:jakarta'

// 또는
implementation 'org.jooq:jooq'
```

#### 테스트 의존성
```gradle
testImplementation 'org.springframework.boot:spring-boot-starter-test'
testImplementation 'org.testcontainers:testcontainers'
testImplementation 'org.testcontainers:junit-jupiter'
testImplementation 'org.testcontainers:postgresql'
```

---

### 🔄 B. 이벤트/비동기 처리

#### Kafka 기반
```gradle
implementation 'org.springframework.kafka:spring-kafka'

// Schema Registry 사용 시
implementation 'io.confluent:kafka-avro-serializer'
implementation 'io.confluent:kafka-protobuf-serializer'
```

#### 스케줄/작업 처리
```gradle
// Spring Batch
implementation 'org.springframework.boot:spring-boot-starter-batch'

// 또는 Quartz
implementation 'org.springframework.boot:spring-boot-starter-quartz'
```

---

### 📁 C. 파일/외부 연동

#### AWS S3
```gradle
implementation 'software.amazon.awssdk:s3'
implementation 'software.amazon.awssdk:sts'
```

#### HTTP 클라이언트
```gradle
// OpenFeign
implementation 'org.springframework.cloud:spring-cloud-starter-openfeign'

// 또는 WebClient (WebFlux)
implementation 'org.springframework.boot:spring-boot-starter-webflux'
```

#### 회복탄력성
```gradle
implementation 'io.github.resilience4j:resilience4j-spring-boot3'
implementation 'io.github.resilience4j:resilience4j-circuitbreaker'
implementation 'io.github.resilience4j:resilience4j-retry'
implementation 'io.github.resilience4j:resilience4j-timelimiter'
```

---

### 🏗️ D. MSA 전환 시 (필요할 때만)

#### API Gateway
```gradle
implementation 'org.springframework.cloud:spring-cloud-starter-gateway'
```

#### Config / Service Discovery
```gradle
// Spring Cloud Config
implementation 'org.springframework.cloud:spring-cloud-starter-config'

// Service Discovery (Eureka)
implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'

// 또는 Nacos
implementation 'com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery'
implementation 'com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-config'
```

#### 분산 트레이싱 & 중앙 로그
```gradle
// OpenTelemetry
implementation 'io.opentelemetry:opentelemetry-api'
implementation 'io.opentelemetry:opentelemetry-sdk'
implementation 'io.opentelemetry:opentelemetry-exporter-jaeger'

// 로깅 (ELK/OpenSearch 연동)
implementation 'net.logstash.logback:logstash-logback-encoder'
```

---

## 3. 프로젝트별 권장 조합

### 🚀 신규 프로젝트 (웹 API)
- **A번들** + **B번들** (Kafka) + **C번들** (Resilience4j)

### 📱 모바일 백엔드
- **A번들** + **C번들** (S3, WebClient) + **B번들** (Quartz)

### 🔄 이벤트 기반 시스템
- **A번들** + **B번들** (Kafka) + **D번들** (Service Discovery)

### 🏢 엔터프라이즈 시스템
- **A번들** + **B번들** (Batch) + **C번들** (Resilience4j) + **D번들** (Config)

---

## 4. 버전 관리 전략

### Spring Boot 버전
- **현재**: Spring Boot 3.2.x (Java 21 지원)
- **다음 LTS**: Spring Boot 3.4.x (2024년 말 예정)
- **마이그레이션**: Spring Boot 2.7.x → 3.x (Jakarta EE 9+)

### Java 버전
- **권장**: Java 21 LTS (2023년 9월 ~ 2031년 9월)
- **대안**: Java 17 LTS (2021년 9월 ~ 2029년 9월)
- **최소**: Java 17 (Spring Boot 3.x 요구사항)

---

## 5. 성능 최적화 팁

### JPA/Hibernate
- N+1 쿼리 방지: `@EntityGraph`, `fetch join` 활용
- 배치 처리: `@BatchSize`, `hibernate.jdbc.batch_size` 설정
- 2차 캐시: Redis + Hibernate 2nd Level Cache

### 캐싱 전략
- **L1**: JPA Entity Cache
- **L2**: Redis (Hibernate 2nd Level Cache)
- **L3**: Application Cache (Spring Cache + Redis)

### 모니터링
- **메트릭**: Micrometer + Prometheus
- **로깅**: Structured Logging (JSON) + ELK
- **트레이싱**: OpenTelemetry + Jaeger

---

## 6. 보안 고려사항

### 인증/인가
- **JWT**: Access Token + Refresh Token
- **OAuth2**: Resource Server + Authorization Server 분리
- **CORS**: 프론트엔드 도메인별 설정

### 데이터 보호
- **암호화**: 민감 데이터 AES-256 암호화
- **마스킹**: 로그 출력 시 개인정보 마스킹
- **검증**: 입력값 검증 + SQL Injection 방지

---

## 7. 배포 전략

### 컨테이너화
- **Docker**: Multi-stage build + Alpine Linux
- **Kubernetes**: Helm Charts + ConfigMaps
- **CI/CD**: GitHub Actions + ArgoCD

### 환경별 설정
- **개발**: H2 + 로컬 설정
- **스테이징**: PostgreSQL + 외부 서비스 연동
- **운영**: PostgreSQL + Redis + Kafka + 모니터링

---

## 8. 마이그레이션 체크리스트

### Spring Boot 2.x → 3.x
- [ ] Java 17+ 확인
- [ ] Jakarta EE 9+ 패키지 변경 (`javax.*` → `jakarta.*`)
- [ ] Spring Security 설정 업데이트
- [ ] JPA/Hibernate 설정 검토
- [ ] 테스트 코드 업데이트

### Java 8/11 → 17/21
- [ ] 언어 기능 활용 (Records, Pattern Matching, Text Blocks)
- [ ] GC 튜닝 (G1GC → ZGC/Shenandoah)
- [ ] 모듈 시스템 검토 (필요시)

---

## 9. 참고 자료

### 공식 문서
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA Reference](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)

### 커뮤니티
- [Spring Blog](https://spring.io/blog)
- [Spring Framework GitHub](https://github.com/spring-projects/spring-framework)
- [Spring Boot GitHub](https://github.com/spring-projects/spring-boot)

---

*이 문서는 Spring Boot 프로젝트 설계 시 참고용으로 작성되었습니다. 프로젝트 요구사항에 따라 적절히 조정하여 사용하시기 바랍니다.*
