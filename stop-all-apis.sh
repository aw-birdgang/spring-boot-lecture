#!/bin/bash

echo "🛑 Spring Boot Lecture - 모든 API 종료 중..."

# Gradle 프로세스 종료
echo "📱 Mobile API 종료 중..."
pkill -f "mobile-api:bootRun"

echo "🌐 Web API 종료 중..."
pkill -f "web-api:bootRun"

echo "🔧 Admin API 종료 중..."
pkill -f "admin-api:bootRun"

# Java 프로세스 종료 (포트별)
echo "포트 8081, 8082, 8083에서 실행 중인 Java 프로세스 종료 중..."
lsof -ti:8081 | xargs kill -9 2>/dev/null || echo "포트 8081에서 실행 중인 프로세스가 없습니다."
lsof -ti:8082 | xargs kill -9 2>/dev/null || echo "포트 8082에서 실행 중인 프로세스가 없습니다."
lsof -ti:8083 | xargs kill -9 2>/dev/null || echo "포트 8083에서 실행 중인 프로세스가 없습니다."

echo ""
echo "✅ 모든 API가 종료되었습니다!"
echo ""
echo "🧹 로그 파일 정리:"
rm -f mobile-api.log web-api.log admin-api.log
echo "   로그 파일이 삭제되었습니다."
