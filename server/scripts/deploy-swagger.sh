#!/bin/bash

# swagger
REPOSITORY=/home/bside/311TEN003_for_swagger

# local
#REPOSITORY=/Users/dongseoklee/github/311TEN003

# common
PROJECT_LOCATION_FOLDER_NAME=server
PROJECT_NAME=bside_311

cd $REPOSITORY/$PROJECT_LOCATION_FOLDER_NAME/

echo "> Git reset --hard"
git reset --hard

echo "> Git Pull"

git pull

echo "Release Version Updated"
#grep "^Release" ./releasenote.txt | tail -1 > ./src/main/frontend/public/latestReleaseVer.txt


echo "> gradlew, deploy-swagger.sh 권한 변경 "
chmod 777 gradlew
chmod 774 scripts/deploy-swagger.sh

echo "> 프로젝트 Build 시작"
./gradlew build --exclude-task test

echo "> Build 파일 복사"

cp ./build/libs/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

#CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)
CURRENT_PID=$(pgrep -f "active=swagger")

echo "$CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -2 $CURRENT_PID"
    kill -9 "$CURRENT_PID"
    sleep 10
fi

echo "> 새 어플리케이션 배포"

# JAR_NAME=$(ls $REPOSITORY/ |grep jar | tail -n 1)
JAR_NAME=$(ls $REPOSITORY/ |grep ${PROJECT_NAME}.*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

nohup java -jar $REPOSITORY/"$JAR_NAME"  --spring.profiles.active=swagger &

