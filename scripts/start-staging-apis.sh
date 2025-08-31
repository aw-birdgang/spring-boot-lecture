#!/bin/bash

echo "🚀 Spring Boot Lecture - 스테이징 환경 API 시작 중..."

# 환경 변수 설정
export SPRING_PROFILES_ACTIVE=staging
export LOG_LEVEL=INFO

# 로그 디렉토리 생성
mkdir -p logs
mkdir -p data/staging

# Mobile API 시작
echo "📱 Mobile API 시작 중... (포트: 9081, 프로필: staging)"
./gradlew :projects:mobile-api:bootRun --args='--spring.profiles.active=staging' > logs/mobile-api-staging.log 2>&1 &
MOBILE_PID=$!

# 잠시 대기
sleep 5

# Web API 시작
echo "🌐 Web API 시작 중... (포트: 9082, 프로필: staging)"
./gradlew :projects:web-api:bootRun --args='--spring.profiles.active=staging' > logs/web-api-staging.log 2>&1 &
WEB_PID=$!

# 잠시 대기
sleep 5

# Admin API 시작
echo "🔧 Admin API 시작 중... (포트: 9083, 프로필: staging)"
./gradlew :projects:admin-api:bootRun --args='--spring.profiles.active=staging' > logs/admin-api-staging.log 2>&1 &
ADMIN_PID=$!

echo ""
echo "✅ 스테이징 환경 API가 모두 시작되었습니다!"
echo ""
echo "📊 실행 중인 프로세스:"
echo "   Mobile API (포트 9081): PID $MOBILE_PID"
echo "   Web API (포트 9082): PID $WEB_PID"
echo "   Admin API (포트 9083): PID $ADMIN_PID"
echo ""
echo "🔗 접근 URL:"
echo "   Mobile API: http://localhost:9081/staging"
echo "   Web API: http://localhost:9082/staging"
echo "   Admin API: http://localhost:9083/staging"
echo ""
echo "📚 Swagger UI:"
echo "   Mobile API: http://localhost:9081/staging/swagger-ui.html"
echo "   Web API: http://localhost:9082/staging/swagger-ui.html"
echo "   Admin API: http://localhost:9083/staging/swagger-ui.html"
echo ""
echo "🗄️ H2 Console:"
echo "   Mobile API: http://localhost:9081/staging/h2-console"
echo "   Web API: http://localhost:9082/staging/h2-console"
echo "   Admin API: http://localhost:9083/staging/h2-console"
echo ""
echo "📝 로그 파일:"
echo "   Mobile API: logs/mobile-api-staging.log"
echo "   Web API: logs/web-api-staging.log"
echo "   Admin API: logs/admin-api-staging.log"
echo ""
echo "🔍 로그 실시간 확인:"
echo "   tail -f logs/mobile-api-staging.log"
echo "   tail -f logs/web-api-staging.log"
echo "   tail -f logs/admin-api-staging.log"
echo ""
echo "🛑 스테이징 환경 API를 종료하려면:"
echo "   kill $MOBILE_PID $WEB_PID $ADMIN_PID"
echo "   또는: ./scripts/stop-staging-apis.sh"
echo ""

# PID 파일 저장
echo $MOBILE_PID > logs/mobile-api-staging.pid
echo $WEB_PID > logs/web-api-staging.pid
echo $ADMIN_PID > logs/admin-api-staging.pid

# 프로세스가 실행 중인지 확인
wait
