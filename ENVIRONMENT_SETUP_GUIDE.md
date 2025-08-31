# Spring Boot 프로젝트 환경별 구분 설정 가이드

## 📋 개요

이 문서는 Spring Boot Lecture 프로젝트에서 **개발(dev)**, **스테이징(staging)**, **운영(prod)** 환경을 구분하여 설정하는 방법을 단계별로 설명합니다.

## 🎯 목표

- 환경별 설정 파일 분리
- 환경별 실행 스크립트 제공
- 환경별 보안 및 로깅 설정
- CI/CD 파이프라인 지원
- Docker 컨테이너화 지원

## 📁 프로젝트 구조 (수정 후)

```
spring-boot-lecture/
├── projects/
│   ├── web-api/
│   │   └── src/main/resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       ├── application-staging.properties    # 새로 추가
│   │       └── application-prod.properties
│   ├── mobile-api/
│   │   └── src/main/resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       ├── application-staging.properties    # 새로 추가
│   │       └── application-prod.properties
│   └── admin-api/
│       └── src/main/resources/
│           ├── application.properties
│           ├── application-dev.properties
│           ├── application-staging.properties    # 새로 추가
│           └── application-prod.properties
├── scripts/
│   ├── start-dev-apis.sh      # 새로 추가
│   ├── start-staging-apis.sh  # 새로 추가
│   ├── start-prod-apis.sh     # 새로 추가
│   ├── stop-dev-apis.sh       # 새로 추가
│   ├── stop-staging-apis.sh   # 새로 추가
│   └── stop-prod-apis.sh      # 새로 추가
├── docker/
│   ├── Dockerfile.dev         # 새로 추가
│   ├── Dockerfile.staging     # 새로 추가
│   ├── Dockerfile.prod        # 새로 추가
│   ├── docker-compose.dev.yml     # 새로 추가
│   ├── docker-compose.staging.yml # 새로 추가
│   └── docker-compose.prod.yml    # 새로 추가
├── .env.dev                   # 새로 추가
├── .env.staging               # 새로 추가
├── .env.prod                  # 새로 추가
└── logback-spring.xml         # 새로 추가
```

## 🔧 단계별 수정 목록

### Phase 1: 환경별 설정 파일 추가
- [ ] Staging 환경 설정 파일 생성
- [ ] 공통 설정 파일 분리
- [ ] 환경별 데이터베이스 설정

### Phase 2: 실행 스크립트 개선
- [ ] 환경별 시작 스크립트 생성
- [ ] 환경별 종료 스크립트 생성
- [ ] 기존 스크립트 수정

### Phase 3: Gradle 빌드 설정
- [ ] 환경별 빌드 태스크 추가
- [ ] 환경별 JAR 생성 설정
- [ ] 프로필 기반 빌드 지원

### Phase 4: 로깅 및 모니터링
- [ ] Logback 설정 파일 생성
- [ ] 환경별 로그 레벨 설정
- [ ] Actuator 설정 조정

### Phase 5: 보안 설정
- [ ] 환경별 Security 설정
- [ ] 환경변수 기반 인증
- [ ] API 문서 설정

### Phase 6: Docker 지원
- [ ] 환경별 Dockerfile 생성
- [ ] Docker Compose 설정
- [ ] 컨테이너 환경변수 설정

### Phase 7: CI/CD 파이프라인
- [ ] GitHub Actions 워크플로우
- [ ] 환경별 배포 자동화
- [ ] 테스트 자동화

### Phase 8: 문서화
- [ ] 개발 가이드 업데이트
- [ ] 배포 가이드 생성
- [ ] 환경별 설정 가이드

## 🚀 환경별 특징

### 개발 환경 (dev)
- **목적**: 개발자 로컬 개발
- **데이터베이스**: H2 인메모리
- **로깅**: DEBUG 레벨
- **보안**: 기본 인증
- **캐시**: Caffeine (로컬)
- **포트**: 8081, 8082, 8083

### 스테이징 환경 (staging)
- **목적**: 테스트 및 검증
- **데이터베이스**: H2 파일 또는 테스트 DB
- **로깅**: INFO 레벨
- **보안**: 환경변수 기반
- **캐시**: Redis
- **포트**: 8081, 8082, 8083

### 운영 환경 (prod)
- **목적**: 실제 서비스 운영
- **데이터베이스**: PostgreSQL/MySQL
- **로깅**: WARN/ERROR 레벨
- **보안**: JWT/OAuth2
- **캐시**: Redis 클러스터
- **포트**: 환경변수 기반

## 📝 실행 방법

### 개발 환경
```bash
# 모든 API 시작
./scripts/start-dev-apis.sh

# 특정 API만 시작
./gradlew :projects:web-api:bootRun --args='--spring.profiles.active=dev'
```

### 스테이징 환경
```bash
# 모든 API 시작
./scripts/start-staging-apis.sh

# 특정 API만 시작
./gradlew :projects:web-api:bootRun --args='--spring.profiles.active=staging'
```

### 운영 환경
```bash
# 모든 API 시작
./scripts/start-prod-apis.sh

# 특정 API만 시작
./gradlew :projects:web-api:bootRun --args='--spring.profiles.active=prod'
```

## 🔒 보안 고려사항

### 환경변수 관리
- 민감한 정보는 환경변수로 관리
- `.env` 파일은 `.gitignore`에 추가
- 운영 환경에서는 시크릿 관리 시스템 사용

### API 문서
- 개발/스테이징: Swagger UI 활성화
- 운영: Swagger UI 비활성화 또는 제한적 활성화

### 데이터베이스
- 개발: 인메모리 DB 사용
- 스테이징: 테스트용 DB 사용
- 운영: 실제 운영 DB 사용

## 📊 모니터링

### Actuator 엔드포인트
- **개발**: 모든 엔드포인트 활성화
- **스테이징**: health, info, metrics 활성화
- **운영**: health, info만 활성화

### 로그 관리
- **개발**: 콘솔 출력
- **스테이징**: 파일 + 콘솔 출력
- **운영**: 파일 출력 + 로그 수집 시스템 연동

## 🐳 Docker 지원

### 환경별 이미지
- `spring-boot-lecture:dev`
- `spring-boot-lecture:staging`
- `spring-boot-lecture:prod`

### 컨테이너 실행
```bash
# 개발 환경
docker-compose -f docker/docker-compose.dev.yml up

# 스테이징 환경
docker-compose -f docker/docker-compose.staging.yml up

# 운영 환경
docker-compose -f docker/docker-compose.prod.yml up
```

## 🔄 CI/CD 파이프라인

### GitHub Actions 워크플로우
1. **개발**: 코드 커밋 시 자동 테스트
2. **스테이징**: main 브랜치 머지 시 자동 배포
3. **운영**: 태그 생성 시 수동 승인 후 배포

### 배포 단계
1. 코드 빌드
2. 단위 테스트 실행
3. 통합 테스트 실행
4. 환경별 배포
5. 헬스 체크
6. 롤백 준비

## 📚 추가 자료

- [Spring Boot Profiles 공식 문서](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.profiles)
- [Spring Boot Actuator 가이드](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Docker Spring Boot 가이드](https://spring.io/guides/gs/spring-boot-docker/)

---

**작성일**: 2024년 12월  
**버전**: 1.0.0  
**작성자**: Spring Boot Lecture Team
