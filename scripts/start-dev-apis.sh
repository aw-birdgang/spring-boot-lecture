#!/bin/bash

echo "🚀 Spring Boot Lecture - 개발 환경 API 시작 중..."

# 환경 변수 설정
export SPRING_PROFILES_ACTIVE=dev
export LOG_LEVEL=DEBUG

# 로그 디렉토리 생성
mkdir -p logs
mkdir -p data/dev

# Mobile API 시작
echo "📱 Mobile API 시작 중... (포트: 8081, 프로필: dev)"
./gradlew :projects:mobile-api:bootRun --args='--spring.profiles.active=dev' > logs/mobile-api-dev.log 2>&1 &
MOBILE_PID=$!

# 잠시 대기
sleep 5

# Web API 시작
echo "🌐 Web API 시작 중... (포트: 8082, 프로필: dev)"
./gradlew :projects:web-api:bootRun --args='--spring.profiles.active=dev' > logs/web-api-dev.log 2>&1 &
WEB_PID=$!

# 잠시 대기
sleep 5

# Admin API 시작
echo "🔧 Admin API 시작 중... (포트: 8083, 프로필: dev)"
./gradlew :projects:admin-api:bootRun --args='--spring.profiles.active=dev' > logs/admin-api-dev.log 2>&1 &
ADMIN_PID=$!

echo ""
echo "✅ 개발 환경 API가 모두 시작되었습니다!"
echo ""
echo "📊 실행 중인 프로세스:"
echo "   Mobile API (포트 8081): PID $MOBILE_PID"
echo "   Web API (포트 8082): PID $WEB_PID"
echo "   Admin API (포트 8083): PID $ADMIN_PID"
echo ""
echo "🔗 접근 URL:"
echo "   Mobile API: http://localhost:8081/dev"
echo "   Web API: http://localhost:8082/dev"
echo "   Admin API: http://localhost:8083/dev"
echo ""
echo "📚 Swagger UI:"
echo "   Mobile API: http://localhost:8081/dev/swagger-ui.html"
echo "   Web API: http://localhost:8082/dev/swagger-ui.html"
echo "   Admin API: http://localhost:8083/dev/swagger-ui.html"
echo ""
echo "🗄️ H2 Console:"
echo "   Mobile API: http://localhost:8081/dev/h2-console"
echo "   Web API: http://localhost:8082/dev/h2-console"
echo "   Admin API: http://localhost:8083/dev/h2-console"
echo ""
echo "📝 로그 파일:"
echo "   Mobile API: logs/mobile-api-dev.log"
echo "   Web API: logs/web-api-dev.log"
echo "   Admin API: logs/admin-api-dev.log"
echo ""
echo "🔍 로그 실시간 확인:"
echo "   tail -f logs/mobile-api-dev.log"
echo "   tail -f logs/web-api-dev.log"
echo "   tail -f logs/admin-api-dev.log"
echo ""
echo "🛑 개발 환경 API를 종료하려면:"
echo "   kill $MOBILE_PID $WEB_PID $ADMIN_PID"
echo "   또는: ./scripts/stop-dev-apis.sh"
echo ""

# PID 파일 저장
echo $MOBILE_PID > logs/mobile-api-dev.pid
echo $WEB_PID > logs/web-api-dev.pid
echo $ADMIN_PID > logs/admin-api-dev.pid

# 프로세스가 실행 중인지 확인
wait
