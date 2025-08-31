#!/bin/bash

echo "🛑 Spring Boot Lecture - 운영 환경 API 종료 중..."

# 함수: 프로세스 종료
stop_process() {
    local service_name=$1
    local pid_file=$2
    
    if [ -f "$pid_file" ]; then
        local pid=$(cat "$pid_file")
        if kill -0 "$pid" 2>/dev/null; then
            echo "🔄 $service_name 종료 중... (PID: $pid)"
            kill "$pid"
            
            # 정상 종료 대기 (최대 15초 - 운영환경은 더 오래 대기)
            local count=0
            while kill -0 "$pid" 2>/dev/null && [ $count -lt 15 ]; do
                sleep 1
                ((count++))
            done
            
            if kill -0 "$pid" 2>/dev/null; then
                echo "⚠️  $service_name 강제 종료 중... (PID: $pid)"
                kill -9 "$pid"
                sleep 2
                
                if kill -0 "$pid" 2>/dev/null; then
                    echo "❌ $service_name 종료 실패 (PID: $pid)"
                    return 1
                else
                    echo "✅ $service_name 강제 종료 완료"
                fi
            else
                echo "✅ $service_name 정상 종료 완료"
            fi
        else
            echo "ℹ️  $service_name는 이미 종료되었습니다."
        fi
        rm -f "$pid_file"
    else
        echo "ℹ️  $service_name PID 파일을 찾을 수 없습니다."
    fi
    return 0
}

# 각 API 종료
stop_process "Mobile API" "logs/mobile-api-prod.pid"
stop_process "Web API" "logs/web-api-prod.pid"
stop_process "Admin API" "logs/admin-api-prod.pid"

echo ""
echo "✅ 운영 환경 API가 모두 종료되었습니다!"
echo ""
echo "🧹 로그 파일 정리:"
echo "   logs/mobile-api-prod.log"
echo "   logs/web-api-prod.log"
echo "   logs/admin-api-prod.log"
echo ""
echo "💡 로그 파일을 삭제하려면:"
echo "   rm logs/*-prod.log"
echo ""
echo "⚠️  운영 환경 로그는 보관을 권장합니다."
echo ""
echo "🔍 남은 프로세스 확인:"
echo "   ps aux | grep gradle | grep -v grep"
