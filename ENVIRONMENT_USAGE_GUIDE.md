# Spring Boot Lecture - 환경별 설정 사용 가이드

## 🚀 빠른 시작

### 개발 환경 실행
```bash
# 모든 API 시작 (개발 환경)
./scripts/start-dev-apis.sh

# 특정 API만 시작
./gradlew :projects:web-api:bootRunDev
```

### 스테이징 환경 실행
```bash
# 모든 API 시작 (스테이징 환경)
./scripts/start-staging-apis.sh

# 특정 API만 시작
./gradlew :projects:web-api:bootRunStaging
```

### 운영 환경 실행
```bash
# 환경변수 설정 후 실행
source env.prod
./scripts/start-prod-apis.sh

# 특정 API만 시작
./gradlew :projects:web-api:bootRunProd
```

## 📋 환경별 특징

### 🛠️ 개발 환경 (dev)
- **목적**: 개발자 로컬 개발
- **데이터베이스**: H2 인메모리
- **로깅**: DEBUG 레벨
- **보안**: 기본 인증
- **캐시**: Caffeine (로컬)
- **포트**: 8081, 8082, 8083
- **URL 경로**: `/dev`
- **Swagger**: 활성화

### 🧪 스테이징 환경 (staging)
- **목적**: 테스트 및 검증
- **데이터베이스**: H2 파일 기반
- **로깅**: INFO 레벨
- **보안**: 환경변수 기반
- **캐시**: Redis
- **포트**: 9081, 9082, 9083
- **URL 경로**: `/staging`
- **Swagger**: 활성화

### 🏭 운영 환경 (prod)
- **목적**: 실제 서비스 운영
- **데이터베이스**: PostgreSQL/MySQL
- **로깅**: WARN/ERROR 레벨
- **보안**: JWT/OAuth2
- **캐시**: Redis 클러스터
- **포트**: 10081, 10082, 10083 (환경변수 기반)
- **URL 경로**: `/prod`
- **Swagger**: 비활성화

## 🌐 환경별 접근 주소

### 개발 환경 (dev)
```bash
# API 접근
http://localhost:8081/dev  # Mobile API
http://localhost:8082/dev  # Web API
http://localhost:8083/dev  # Admin API

# Swagger UI
http://localhost:8081/dev/swagger-ui.html  # Mobile API
http://localhost:8082/dev/swagger-ui.html  # Web API
http://localhost:8083/dev/swagger-ui.html  # Admin API

# H2 Console
http://localhost:8081/dev/h2-console  # Mobile API
http://localhost:8082/dev/h2-console  # Web API
http://localhost:8083/dev/h2-console  # Admin API

# Actuator
http://localhost:8081/dev/actuator/health  # Mobile API
http://localhost:8082/dev/actuator/health  # Web API
http://localhost:8083/dev/actuator/health  # Admin API
```

### 스테이징 환경 (staging)
```bash
# API 접근
http://localhost:9081/staging  # Mobile API
http://localhost:9082/staging  # Web API
http://localhost:9083/staging  # Admin API

# Swagger UI
http://localhost:9081/staging/swagger-ui.html  # Mobile API
http://localhost:9082/staging/swagger-ui.html  # Web API
http://localhost:9083/staging/swagger-ui.html  # Admin API

# H2 Console
http://localhost:9081/staging/h2-console  # Mobile API
http://localhost:9082/staging/h2-console  # Web API
http://localhost:9083/staging/h2-console  # Admin API

# Actuator
http://localhost:9081/staging/actuator/health  # Mobile API
http://localhost:9082/staging/actuator/health  # Web API
http://localhost:9083/staging/actuator/health  # Admin API
```

### 운영 환경 (prod)
```bash
# API 접근
http://localhost:10081/prod  # Mobile API
http://localhost:10082/prod  # Web API
http://localhost:10083/prod  # Admin API

# Swagger UI (비활성화)
# 접근 불가능

# H2 Console (비활성화)
# 접근 불가능

# Actuator
http://localhost:10081/prod/actuator/health  # Mobile API
http://localhost:10082/prod/actuator/health  # Web API
http://localhost:10083/prod/actuator/health  # Admin API
```

## 🔧 환경변수 설정

### 개발 환경
```bash
# 환경변수 파일 로드
source env.dev

# 또는 직접 설정
export SPRING_PROFILES_ACTIVE=dev
export LOG_LEVEL=DEBUG
```

### 스테이징 환경
```bash
# 환경변수 파일 로드
source env.staging

# 또는 직접 설정
export SPRING_PROFILES_ACTIVE=staging
export LOG_LEVEL=INFO
```

### 운영 환경
```bash
# 환경변수 파일 로드
source env.prod

# 필수 환경변수 설정
export DB_HOST=your-prod-db-host
export DB_USERNAME=your-prod-db-username
export DB_PASSWORD=your-prod-db-password
export REDIS_HOST=your-prod-redis-host
export REDIS_PASSWORD=your-prod-redis-password
export ADMIN_USERNAME=your-prod-admin
export ADMIN_PASSWORD=your-prod-admin-password
```

## 📊 모니터링 및 로그

### 로그 확인
```bash
# 실시간 로그 확인
tail -f logs/mobile-api-dev.log
tail -f logs/web-api-staging.log
tail -f logs/admin-api-prod.log

# 로그 파일 정리
rm logs/*-dev.log      # 개발 로그 삭제
rm logs/*-staging.log  # 스테이징 로그 삭제
rm logs/*-prod.log     # 운영 로그 삭제 (주의!)
```

### 헬스 체크
```bash
# 개발 환경
curl http://localhost:8081/dev/actuator/health  # Mobile API
curl http://localhost:8082/dev/actuator/health  # Web API
curl http://localhost:8083/dev/actuator/health  # Admin API

# 스테이징 환경
curl http://localhost:9081/staging/actuator/health  # Mobile API
curl http://localhost:9082/staging/actuator/health  # Web API
curl http://localhost:9083/staging/actuator/health  # Admin API

# 운영 환경
curl http://localhost:10081/prod/actuator/health  # Mobile API
curl http://localhost:10082/prod/actuator/health  # Web API
curl http://localhost:10083/prod/actuator/health  # Admin API
```

## 🗄️ 데이터베이스 접근

### H2 Console 접근
```bash
# 개발 환경
http://localhost:8081/dev/h2-console
http://localhost:8082/dev/h2-console
http://localhost:8083/dev/h2-console

# 스테이징 환경
http://localhost:9081/staging/h2-console
http://localhost:9082/staging/h2-console
http://localhost:9083/staging/h2-console

# 운영 환경 (H2 Console 비활성화)
```

### 데이터베이스 설정
- **개발**: `jdbc:h2:mem:db-name`
- **스테이징**: `jdbc:h2:file:./data/staging/db-name`
- **운영**: `jdbc:postgresql://host:port/db-name`

## 🔒 보안 설정

### 인증 정보
- **개발**: admin/admin123
- **스테이징**: staging-admin/staging123
- **운영**: 환경변수 기반

### API 문서 접근
- **개발**: http://localhost:8081/dev/swagger-ui.html
- **스테이징**: http://localhost:9081/staging/swagger-ui.html
- **운영**: 비활성화

## 🐳 Docker 지원 (향후 추가 예정)

```bash
# 개발 환경
docker-compose -f docker/docker-compose.dev.yml up

# 스테이징 환경
docker-compose -f docker/docker-compose.staging.yml up

# 운영 환경
docker-compose -f docker/docker-compose.prod.yml up
```

## 🔄 CI/CD 파이프라인 (향후 추가 예정)

### GitHub Actions 워크플로우
1. **개발**: 코드 커밋 시 자동 테스트
2. **스테이징**: main 브랜치 머지 시 자동 배포
3. **운영**: 태그 생성 시 수동 승인 후 배포

## 🚨 문제 해결

### 일반적인 문제들

#### 1. 포트 충돌
```bash
# 포트 사용 확인
lsof -i :8081  # 개발 환경
lsof -i :9081  # 스테이징 환경
lsof -i :10081 # 운영 환경

# 프로세스 종료
kill -9 <PID>
```

#### 2. 환경변수 문제
```bash
# 환경변수 확인
echo $SPRING_PROFILES_ACTIVE
echo $DB_HOST
echo $REDIS_HOST

# 환경변수 파일 다시 로드
source env.dev
```

#### 3. 로그 파일 문제
```bash
# 로그 디렉토리 생성
mkdir -p logs
mkdir -p data/dev
mkdir -p data/staging
mkdir -p data/prod

# 권한 확인
chmod 755 logs/
chmod 755 data/
```

#### 4. Redis 연결 문제
```bash
# Redis 서버 상태 확인
redis-cli ping

# Redis 서버 시작 (macOS)
brew services start redis

# Redis 서버 시작 (Linux)
sudo systemctl start redis
```

#### 5. 데이터베이스 파일 잠금 문제
```bash
# 데이터베이스 파일 정리
rm -rf data/staging/
rm -rf data/prod/

# 또는 H2 서버 모드 사용
# application-staging.properties에서 AUTO_SERVER=TRUE 설정
```

## 📚 추가 자료

- [Spring Boot Profiles 공식 문서](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.profiles)
- [Spring Boot Actuator 가이드](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Logback 설정 가이드](https://logback.qos.ch/manual/configuration.html)
- [Spring Security 가이드](https://docs.spring.io/spring-security/site/docs/current/reference/html5/)

---

**작성일**: 2024년 12월  
**버전**: 1.0.0  
**작성자**: Spring Boot Lecture Team
