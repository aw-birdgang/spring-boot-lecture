# Spring Boot Lecture - 빌드 및 실행 가이드

## 📋 **목차**
1. [프로젝트 개요](#프로젝트-개요)
2. [시스템 요구사항](#시스템-요구사항)
3. [프로젝트 구조](#프로젝트-구조)
4. [빌드 방법](#빌드-방법)
5. [실행 방법](#실행-방법)
6. [API 문서 및 접근](#api-문서-및-접근)
7. [개발 환경 설정](#개발-환경-설정)
8. [문제 해결](#문제-해결)

---

## 🎯 **프로젝트 개요**

Spring Boot Lecture는 **멀티 모듈 아키텍처**를 기반으로 한 교육용 프로젝트입니다. 
하나의 프로젝트에서 여러 개의 독립적인 Spring Boot 애플리케이션을 관리할 수 있도록 설계되었습니다.

### **주요 특징**
- ✅ **멀티 모듈 구조**: 공통 모듈과 독립적인 API 모듈 분리
- ✅ **마이크로서비스 준비**: 각 API를 독립적으로 배포 가능
- ✅ **공통 라이브러리**: Database, Shared Library, Common 모듈 재사용
- ✅ **API 문서화**: Swagger UI 자동 생성
- ✅ **모니터링**: Actuator 엔드포인트 제공

---

## 💻 **시스템 요구사항**

### **필수 요구사항**
- **Java**: 17 이상
- **Gradle**: 7.x 이상 (Wrapper 포함)
- **메모리**: 최소 4GB RAM
- **디스크**: 최소 1GB 여유 공간

### **선택 요구사항**
- **Redis**: 캐싱용 (선택사항)
- **IDE**: IntelliJ IDEA, Eclipse, VS Code

### **운영체제**
- ✅ **macOS**: 10.15 이상
- ✅ **Linux**: Ubuntu 18.04 이상
- ✅ **Windows**: Windows 10 이상

---

## 🏗️ **프로젝트 구조**

```
spring-boot-lecture/
├── 📁 common/                    # 공통 모듈 (Spring Boot 공통 기능)
│   ├── config/                   # 공통 설정 (OpenAPI, Security 등)
│   ├── exception/                # 전역 예외 처리
│   └── service/                  # 공통 서비스 (Hello Service 등)
├── 📁 database/                  # 데이터베이스 모듈 (JPA 엔티티, Repository)
│   ├── entity/                   # JPA 엔티티 (User, Post)
│   └── repository/               # 데이터 접근 계층
├── 📁 shared-lib/                # 공유 라이브러리 (순수 Java DTO)
│   └── dto/                      # 데이터 전송 객체
├── 📁 projects/                  # API 프로젝트들
│   ├── 📱 mobile-api/           # 모바일 API (포트: 8081)
│   │   ├── controller/           # 모바일 전용 컨트롤러
│   │   ├── service/              # 모바일 전용 서비스
│   │   └── application.properties # 모바일 API 설정
│   ├── 🌐 web-api/              # 웹 API (포트: 8082)
│   │   ├── controller/           # 웹 전용 컨트롤러
│   │   ├── service/              # 웹 전용 서비스
│   │   └── application.properties # 웹 API 설정
│   └── 🔧 admin-api/            # 관리자 API (포트: 8083)
│       ├── controller/           # 관리자 전용 컨트롤러
│       ├── service/              # 관리자 전용 서비스
│       └── application.properties # 관리자 API 설정
├── 📄 build.gradle              # 루트 빌드 설정
├── 📄 settings.gradle           # 멀티 모듈 설정
├── 🚀 start-all-apis.sh         # 모든 API 시작 스크립트
├── 🛑 stop-all-apis.sh          # 모든 API 종료 스크립트
└── 📄 README.md                 # 프로젝트 설명서
```

### **모듈별 역할**

| 모듈 | 역할 | 특징 |
|------|------|------|
| **common** | Spring Boot 공통 기능 | Spring 의존성, 설정, 예외 처리 |
| **database** | 데이터베이스 관련 | JPA 엔티티, Repository |
| **shared-lib** | 순수 Java 라이브러리 | DTO, 유틸리티 (Spring 독립적) |
| **mobile-api** | 모바일 전용 API | 캐싱 최적화, Actuator |
| **web-api** | 웹 전용 API | Security, Thymeleaf |
| **admin-api** | 관리자 전용 API | 고급 모니터링, 관리 기능 |

---

## 🔨 **빌드 방법**

### **1. 전체 프로젝트 빌드**

```bash
# 전체 프로젝트 빌드 (테스트 포함)
./gradlew build

# 전체 프로젝트 빌드 (테스트 제외)
./gradlew build -x test

# 클린 빌드
./gradlew clean build
```

### **2. 개별 모듈 빌드**

```bash
# 공통 모듈 빌드
./gradlew :common:build

# 데이터베이스 모듈 빌드
./gradlew :database:build

# 공유 라이브러리 빌드
./gradlew :shared-lib:build

# Mobile API 빌드
./gradlew :projects:mobile-api:build

# Web API 빌드
./gradlew :projects:web-api:build

# Admin API 빌드
./gradlew :projects:admin-api:build
```

### **3. JAR 파일 생성**

```bash
# Mobile API JAR 생성
./gradlew :projects:mobile-api:bootJar

# Web API JAR 생성
./gradlew :projects:web-api:bootJar

# Admin API JAR 생성
./gradlew :projects:admin-api:bootJar
```

### **4. 의존성 확인**

```bash
# 의존성 트리 확인
./gradlew dependencies

# 특정 모듈 의존성 확인
./gradlew :projects:mobile-api:dependencies
```

---

## 🚀 **실행 방법**

### **1. 모든 API 동시 실행 (권장)**

```bash
# 모든 API 시작
./start-all-apis.sh

# 모든 API 종료
./stop-all-apis.sh
```

### **2. 개별 API 실행**

```bash
# Mobile API 실행 (포트: 8081)
./gradlew :projects:mobile-api:bootRun

# Web API 실행 (포트: 8082)
./gradlew :projects:web-api:bootRun

# Admin API 실행 (포트: 8083)
./gradlew :projects:admin-api:bootRun
```

### **3. 백그라운드 실행**

```bash
# Mobile API 백그라운드 실행
./gradlew :projects:mobile-api:bootRun &
MOBILE_PID=$!

# Web API 백그라운드 실행
./gradlew :projects:web-api:bootRun &
WEB_PID=$!

# Admin API 백그라운드 실행
./gradlew :projects:admin-api:bootRun &
ADMIN_PID=$!

echo "모든 API가 실행되었습니다!"
echo "Mobile API PID: $MOBILE_PID"
echo "Web API PID: $WEB_PID"
echo "Admin API PID: $ADMIN_PID"
```

### **4. JAR 파일로 실행**

```bash
# JAR 파일 생성 후 실행
./gradlew :projects:mobile-api:bootJar
java -jar projects/mobile-api/build/libs/mobile-api-0.0.1-SNAPSHOT.jar

./gradlew :projects:web-api:bootJar
java -jar projects/web-api/build/libs/web-api-0.0.1-SNAPSHOT.jar

./gradlew :projects:admin-api:bootJar
java -jar projects/admin-api/build/libs/admin-api-0.0.1-SNAPSHOT.jar
```

---

## 📚 **API 문서 및 접근**

### **Swagger UI 접근**

| API | URL | 설명 |
|-----|-----|------|
| **Mobile API** | http://localhost:8081/swagger-ui.html | 모바일 전용 API 문서 |
| **Web API** | http://localhost:8082/swagger-ui.html | 웹 전용 API 문서 |
| **Admin API** | http://localhost:8083/swagger-ui.html | 관리자 전용 API 문서 |

### **H2 Console 접근**

| API | URL | 설정 |
|-----|-----|------|
| **Mobile API** | http://localhost:8081/h2-console | JDBC URL: `jdbc:h2:mem:mobile-db` |
| **Web API** | http://localhost:8082/h2-console | JDBC URL: `jdbc:h2:mem:web-db` |
| **Admin API** | http://localhost:8083/h2-console | JDBC URL: `jdbc:h2:mem:admin-db` |

**H2 Console 접속 정보:**
- **Username**: `sa`
- **Password**: (비어있음)

### **Actuator 엔드포인트**

| API | URL | 제공 정보 |
|-----|-----|-----------|
| **Mobile API** | http://localhost:8081/actuator | Health, Info, Caches |
| **Web API** | http://localhost:8082/actuator | Health, Info, Metrics |
| **Admin API** | http://localhost:8083/actuator | Health, Info, Metrics, Env, ConfigProps |

---

## 🛠️ **개발 환경 설정**

### **1. IDE 설정 (IntelliJ IDEA)**

```bash
# 프로젝트 임포트
1. IntelliJ IDEA 실행
2. "Open" 클릭
3. spring-boot-lecture 폴더 선택
4. "Trust Project" 클릭
5. Gradle 동기화 대기
```

### **2. Gradle 설정**

```bash
# Gradle Wrapper 권한 설정
chmod +x gradlew

# Gradle 버전 확인
./gradlew --version
```

### **3. Java 버전 확인**

```bash
# Java 버전 확인
java -version

# JAVA_HOME 설정 확인
echo $JAVA_HOME
```

### **4. 포트 확인**

```bash
# 사용 중인 포트 확인
lsof -i :8081
lsof -i :8082
lsof -i :8083

# 모든 포트 확인
netstat -an | grep LISTEN
```

---

## 🔧 **문제 해결**

### **1. 빌드 오류**

```bash
# 클린 빌드
./gradlew clean build

# 의존성 새로고침
./gradlew --refresh-dependencies

# Gradle 캐시 삭제
rm -rf ~/.gradle/caches/
```

### **2. 포트 충돌**

```bash
# 포트 사용 중인 프로세스 확인
lsof -i :8081
lsof -i :8082
lsof -i :8083

# 프로세스 강제 종료
kill -9 [PID]
```

### **3. 메모리 부족**

```bash
# Gradle 메모리 설정
export GRADLE_OPTS="-Xmx2048m -XX:MaxPermSize=512m"

# JVM 메모리 설정
export JAVA_OPTS="-Xmx2048m -Xms1024m"
```

### **4. 데이터베이스 연결 오류**

```bash
# H2 Console 접속 확인
1. 브라우저에서 http://localhost:8081/h2-console 접속
2. JDBC URL: jdbc:h2:mem:mobile-db 입력
3. Username: sa, Password: (비어있음)
4. Connect 클릭
```

### **5. Spring Security 로그인 문제**

```bash
# Web API, Admin API의 Security 설정 확인
# application.properties에서 기본 계정 정보 확인
spring.security.user.name=admin
spring.security.user.password=admin123
```

---

## 📊 **모니터링 및 로그**

### **1. 로그 확인**

```bash
# 실시간 로그 확인 (스크립트 실행 시)
tail -f mobile-api.log
tail -f web-api.log
tail -f admin-api.log

# 로그 레벨 설정
logging.level.birdgang.spring.lecture=DEBUG
```

### **2. 프로세스 모니터링**

```bash
# 실행 중인 프로세스 확인
ps aux | grep bootRun

# 포트 사용 확인
lsof -i :8081 -i :8082 -i :8083

# 메모리 사용량 확인
top -p $(pgrep -f bootRun)
```

### **3. 성능 모니터링**

```bash
# Actuator 메트릭 확인
curl http://localhost:8081/actuator/metrics
curl http://localhost:8082/actuator/metrics
curl http://localhost:8083/actuator/metrics

# Health 체크
curl http://localhost:8081/actuator/health
curl http://localhost:8082/actuator/health
curl http://localhost:8083/actuator/health
```

---

## 🎯 **빠른 시작 가이드**

### **1분 만에 시작하기**

```bash
# 1. 프로젝트 클론 (이미 있다면 생략)
git clone [repository-url]
cd spring-boot-lecture

# 2. 빌드
./gradlew build -x test

# 3. 모든 API 실행
./start-all-apis.sh

# 4. 브라우저에서 확인
# Mobile API: http://localhost:8081/swagger-ui.html
# Web API: http://localhost:8082/swagger-ui.html
# Admin API: http://localhost:8083/swagger-ui.html

# 5. 종료
./stop-all-apis.sh
```

---

## 📝 **추가 정보**

### **주요 기술 스택**
- **Spring Boot**: 3.0.2
- **Java**: 17
- **Gradle**: 7.x
- **H2 Database**: 인메모리
- **Spring Data JPA**: 데이터 접근
- **SpringDoc OpenAPI**: API 문서화
- **Spring Security**: 보안 (Web, Admin API)
- **Spring Boot Actuator**: 모니터링

### **프로젝트 정보**
- **Group**: `springbootlecture`
- **Version**: `0.0.1-SNAPSHOT`
- **Java Compatibility**: 17

### **지원 브라우저**
- Chrome 90+
- Firefox 88+
- Safari 14+
- Edge 90+

---

## 🤝 **문의 및 지원**

프로젝트 관련 문의사항이나 문제가 발생하면 다음을 확인해주세요:

1. **로그 확인**: 각 API의 로그 파일 확인
2. **포트 충돌**: 8081, 8082, 8083 포트 사용 여부 확인
3. **Java 버전**: Java 17 이상 설치 확인
4. **메모리**: 충분한 메모리 확보

---

**🎉 이제 Spring Boot Lecture 프로젝트를 성공적으로 빌드하고 실행할 수 있습니다!**
