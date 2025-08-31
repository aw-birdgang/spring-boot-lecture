# Spring Boot 프로젝트 빌드 및 실행 가이드

## 📋 목차
- [사전 요구사항](#사전-요구사항)
- [프로젝트 구조](#프로젝트-구조)
- [빌드 방법](#빌드-방법)
- [실행 방법](#실행-방법)
- [테스트 실행](#테스트-실행)
- [문제 해결](#문제-해결)

## 🎯 사전 요구사항

### Java 버전
- **Spring Boot 3.0.2**는 **Java 17 이상**이 필요합니다
- 현재 프로젝트는 Java 17로 설정되어 있습니다

### Java 버전 확인
```bash
java --version
```

### Java 버전 교체 (필요시)
```bash
# SDKMAN 사용 (권장)
sdk use java 17.0.16-tem

# 또는 환경변수로 직접 설정
export JAVA_HOME=/Users/birdgang/Library/Java/JavaVirtualMachines/openjdk-17.0.1/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH
```

## 🏗️ 프로젝트 구조

```
spring-boot-lecture/
├── build.gradle                    # Gradle 빌드 설정
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── birdgang/spring/lecture/
│   │   │       ├── HelloApplication.java      # 메인 애플리케이션
│   │   │       ├── HelloController.java       # REST 컨트롤러
│   │   │       ├── HelloService.java          # 서비스 인터페이스
│   │   │       ├── SimpleHelloService.java    # 기본 서비스 구현
│   │   │       └── HelloDecorator.java        # 데코레이터 서비스
│   │   └── resources/
│   │       └── application.properties         # 애플리케이션 설정
│   └── test/
│       └── java/
│           └── birdgang/spring/lecture/
│               ├── HelloControllerTest.java   # 컨트롤러 테스트
│               ├── HelloServiceTest.java      # 서비스 테스트
│               └── HelloApiTest.java          # API 통합 테스트
└── gradlew                         # Gradle Wrapper
```

## 🔨 빌드 방법

### 1. Gradle Wrapper 사용 (권장)

#### 전체 프로젝트 빌드
```bash
./gradlew build
```

#### 컴파일만 실행 (빌드 생략)
```bash
./gradlew compileJava
```

#### 테스트 제외하고 빌드
```bash
./gradlew build -x test
```

#### 의존성 다운로드만
```bash
./gradlew dependencies
```

### 2. 빌드 결과물 확인
```bash
# JAR 파일 위치
ls -la build/libs/

# 생성된 JAR 파일
spring-boot-lecture-0.0.1-SNAPSHOT.jar
```

## 🚀 실행 방법

### 방법 1: Gradle로 직접 실행 (개발용)

```bash
./gradlew bootRun
```

**장점:**
- 코드 변경 시 자동 재시작 (Spring Boot DevTools)
- 개발 환경에 최적화
- 로그 확인 용이

### 방법 2: JAR 파일로 실행 (프로덕션용)

```bash
# 1단계: 빌드
./gradlew build

# 2단계: JAR 실행
java -jar build/libs/spring-boot-lecture-0.0.1-SNAPSHOT.jar
```

**장점:**
- 독립적인 실행
- 배포 환경에 적합
- 성능 최적화

### 방법 3: 백그라운드 실행

```bash
# 백그라운드에서 실행
nohup java -jar build/libs/spring-boot-lecture-0.0.1-SNAPSHOT.jar > app.log 2>&1 &

# 프로세스 ID 확인
ps aux | grep java

# 로그 확인
tail -f app.log
```

## 🧪 테스트 실행

### 전체 테스트 실행
```bash
./gradlew test
```

### 특정 테스트 클래스만 실행
```bash
# 컨트롤러 테스트만
./gradlew test --tests "HelloControllerTests"

# 서비스 테스트만
./gradlew test --tests "HelloServiceTests"

# API 테스트만
./gradlew test --tests "HelloApiTests"
```

### 테스트 결과 확인
```bash
# 테스트 리포트 확인
open build/reports/tests/test/index.html

# 또는 브라우저에서 직접 열기
# file:///Users/birdgang/Desktop/dev/workspce-birdgang-git/spring-boot-lecture/build/reports/tests/test/index.html
```

## 🌐 애플리케이션 접근

### 기본 URL
- **애플리케이션**: http://localhost:8080
- **API 엔드포인트**: http://localhost:8080/hello

### API 테스트
```bash
# HTTPie 사용 (권장)
http localhost:8080/hello?name=Spring

# 또는 curl 사용
curl "http://localhost:8080/hello?name=Spring"

# 예상 응답: "*Hello Spring*"
```

## 📊 모니터링 및 로그

### 애플리케이션 상태 확인
```bash
# 프로세스 확인
ps aux | grep java

# 포트 사용 확인
lsof -i :8080

# 메모리 사용량
jstat -gc <PID>
```

### 로그 확인
```bash
# 실시간 로그 확인
tail -f logs/spring.log

# 또는 Gradle 실행 시 로그 확인
./gradlew bootRun --info
```

## 🛠️ 문제 해결

### 일반적인 문제들

#### 1. 포트 충돌
```bash
# 8080 포트 사용 중인 프로세스 확인
lsof -i :8080

# 프로세스 종료
kill -9 <PID>
```

#### 2. Java 버전 문제
```bash
# Java 버전 확인
java --version

# JAVA_HOME 확인
echo $JAVA_HOME

# 올바른 Java 버전으로 교체
sdk use java 17.0.16-tem
```

#### 3. Gradle 캐시 문제
```bash
# Gradle 캐시 정리
rm -rf ~/.gradle/caches/

# 프로젝트 빌드 디렉토리 정리
rm -rf build/ .gradle/

# 다시 빌드
./gradlew build
```

#### 4. 의존성 문제
```bash
# 의존성 트리 확인
./gradlew dependencies

# 의존성 새로고침
./gradlew --refresh-dependencies build
```

### 디버깅 모드 실행
```bash
# 디버그 정보와 함께 실행
./gradlew bootRun --debug

# 또는 JVM 옵션 추가
java -Ddebug=true -jar build/libs/spring-boot-lecture-0.0.1-SNAPSHOT.jar
```

## 🔧 고급 설정

### JVM 옵션 설정
```bash
# 메모리 설정
java -Xms512m -Xmx1024m -jar build/libs/spring-boot-lecture-0.0.1-SNAPSHOT.jar

# 프로파일 설정
java -Dspring.profiles.active=dev -jar build/libs/spring-boot-lecture-0.0.1-SNAPSHOT.jar
```

### 환경변수 설정
```bash
export SPRING_PROFILES_ACTIVE=dev
export SERVER_PORT=8081
./gradlew bootRun
```

## 📝 유용한 명령어 모음

```bash
# 프로젝트 정리
./gradlew clean

# 새로 빌드
./gradlew clean build

# 테스트만 실행
./gradlew test

# 애플리케이션 실행
./gradlew bootRun

# 의존성 확인
./gradlew dependencies

# 프로젝트 정보
./gradlew properties

# Gradle 버전 확인
./gradlew --version
```

## 🎉 성공적인 실행 확인

애플리케이션이 성공적으로 실행되면 다음과 같은 로그를 볼 수 있습니다:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.0.2)

2024-01-XX XX:XX:XX.XXX  INFO 12345 --- [           main] b.s.l.HelloApplication                    : Starting HelloApplication using Java 17.0.16
2024-01-XX XX:XX:XX.XXX  INFO 12345 --- [           main] b.s.l.HelloApplication                    : No active profile set, falling back to 1 default profile: "default"
2024-01-XX XX:XX:XX.XXX  INFO 12345 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http)
2024-01-XX XX:XX:XX.XXX  INFO 12345 --- [           main] b.s.l.HelloApplication                    : Started HelloApplication in X.XXX seconds
```

이제 Spring Boot 애플리케이션을 성공적으로 빌드하고 실행할 수 있습니다! 🚀
