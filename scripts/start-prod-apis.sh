#!/bin/bash

echo "🚀 Spring Boot Lecture - 운영 환경 API 시작 중..."

# 환경 변수 설정
export SPRING_PROFILES_ACTIVE=prod
export LOG_LEVEL=WARN

# 로그 디렉토리 생성
mkdir -p logs
mkdir -p data/prod

# 환경변수 확인
if [ -z "$DB_HOST" ] || [ -z "$DB_USERNAME" ] || [ -z "$DB_PASSWORD" ]; then
    echo "❌ 운영 환경 변수가 설정되지 않았습니다."
    echo "다음 환경변수를 설정해주세요:"
    echo "   DB_HOST, DB_USERNAME, DB_PASSWORD"
    echo "   REDIS_HOST, REDIS_PASSWORD"
    echo "   ADMIN_USERNAME, ADMIN_PASSWORD"
    exit 1
fi

# Mobile API 시작
echo "📱 Mobile API 시작 중... (포트: ${MOBILE_API_PORT:-10081}, 프로필: prod)"
./gradlew :projects:mobile-api:bootRun --args='--spring.profiles.active=prod' > logs/mobile-api-prod.log 2>&1 &
MOBILE_PID=$!

# 잠시 대기
sleep 10

# Web API 시작
echo "🌐 Web API 시작 중... (포트: ${WEB_API_PORT:-10082}, 프로필: prod)"
./gradlew :projects:web-api:bootRun --args='--spring.profiles.active=prod' > logs/web-api-prod.log 2>&1 &
WEB_PID=$!

# 잠시 대기
sleep 10

# Admin API 시작
echo "🔧 Admin API 시작 중... (포트: ${ADMIN_API_PORT:-10083}, 프로필: prod)"
./gradlew :projects:admin-api:bootRun --args='--spring.profiles.active=prod' > logs/admin-api-prod.log 2>&1 &
ADMIN_PID=$!

echo ""
echo "✅ 운영 환경 API가 모두 시작되었습니다!"
echo ""
echo "📊 실행 중인 프로세스:"
echo "   Mobile API (포트 ${MOBILE_API_PORT:-10081}): PID $MOBILE_PID"
echo "   Web API (포트 ${WEB_API_PORT:-10082}): PID $WEB_PID"
echo "   Admin API (포트 ${ADMIN_API_PORT:-10083}): PID $ADMIN_PID"
echo ""
echo "🔗 접근 URL:"
echo "   Mobile API: http://localhost:${MOBILE_API_PORT:-10081}/prod"
echo "   Web API: http://localhost:${WEB_API_PORT:-10082}/prod"
echo "   Admin API: http://localhost:${ADMIN_API_PORT:-10083}/prod"
echo ""
echo "📚 Swagger UI: (운영 환경에서는 비활성화됨)"
echo "   Mobile API: http://localhost:${MOBILE_API_PORT:-10081}/prod/swagger-ui.html"
echo "   Web API: http://localhost:${WEB_API_PORT:-10082}/prod/swagger-ui.html"
echo "   Admin API: http://localhost:${ADMIN_API_PORT:-10083}/prod/swagger-ui.html"
echo ""
echo "📝 로그 파일:"
echo "   Mobile API: logs/mobile-api-prod.log"
echo "   Web API: logs/web-api-prod.log"
echo "   Admin API: logs/admin-api-prod.log"
echo ""
echo "🔍 로그 실시간 확인:"
echo "   tail -f logs/mobile-api-prod.log"
echo "   tail -f logs/web-api-prod.log"
echo "   tail -f logs/admin-api-prod.log"
echo ""
echo "🛑 운영 환경 API를 종료하려면:"
echo "   kill $MOBILE_PID $WEB_PID $ADMIN_PID"
echo "   또는: ./scripts/stop-prod-apis.sh"
echo ""

# PID 파일 저장
echo $MOBILE_PID > logs/mobile-api-prod.pid
echo $WEB_PID > logs/web-api-prod.pid
echo $ADMIN_PID > logs/admin-api-prod.pid

# 프로세스가 실행 중인지 확인
wait
