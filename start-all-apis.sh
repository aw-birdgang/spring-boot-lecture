#!/bin/bash

echo "🚀 Spring Boot Lecture - 모든 API 시작 중..."

# Mobile API 시작
echo "📱 Mobile API 시작 중... (포트: 8081)"
./gradlew :projects:mobile-api:bootRun > mobile-api.log 2>&1 &
MOBILE_PID=$!

# 잠시 대기
sleep 5

# Web API 시작
echo "🌐 Web API 시작 중... (포트: 8082)"
./gradlew :projects:web-api:bootRun > web-api.log 2>&1 &
WEB_PID=$!

# 잠시 대기
sleep 5

# Admin API 시작
echo "🔧 Admin API 시작 중... (포트: 8083)"
./gradlew :projects:admin-api:bootRun > admin-api.log 2>&1 &
ADMIN_PID=$!

echo ""
echo "✅ 모든 API가 시작되었습니다!"
echo ""
echo "📊 실행 중인 프로세스:"
echo "   Mobile API (포트 8081): PID $MOBILE_PID"
echo "   Web API (포트 8082): PID $WEB_PID"
echo "   Admin API (포트 8083): PID $ADMIN_PID"
echo ""
echo "🔗 접근 URL:"
echo "   Mobile API: http://localhost:8081"
echo "   Web API: http://localhost:8082"
echo "   Admin API: http://localhost:8083"
echo ""
echo "📚 Swagger UI:"
echo "   Mobile API: http://localhost:8081/swagger-ui.html"
echo "   Web API: http://localhost:8082/swagger-ui.html"
echo "   Admin API: http://localhost:8083/swagger-ui.html"
echo ""
echo "🗄️ H2 Console:"
echo "   Mobile API: http://localhost:8081/h2-console"
echo "   Web API: http://localhost:8082/h2-console"
echo "   Admin API: http://localhost:8083/h2-console"
echo ""
echo "📝 로그 파일:"
echo "   Mobile API: mobile-api.log"
echo "   Web API: web-api.log"
echo "   Admin API: admin-api.log"
echo ""
echo "🛑 모든 API를 종료하려면:"
echo "   kill $MOBILE_PID $WEB_PID $ADMIN_PID"
echo "   또는: ./stop-all-apis.sh"
echo ""

# 프로세스가 실행 중인지 확인
wait
