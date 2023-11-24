# bside_311

* http://localhost:8080/swagger-ui/index.html

* http://localhost:8080/test

# 수동 배포 방법 정리.(개선 필요.)

```sh
# 1. 현재 구동중인 프로세스 종료.(추후 무중단 배포로..)
cd /home/bside/311TEN003/server
ps -ef |grep java
sudo kill -9 114693

# 2. 프로젝트 최신 소스 받기.
git pull

# 3. 프로젝트 빌드.
./gradlew build --exclude-task test

# 4. 
## 4.1 커밋이력으로 entity의 변경 사항 확인.
## 4.1.1 변경 사항이 있으면, 변경된 entity를 기준으로 DB 최신화.
## 4.2 초기 데이터 세팅 여부 확인.(현재 초기 데이터 -> alcohol_type 테이블)

# 5. 빌드된 jar 파일 실행.
nohup java  -jar build/libs/bside_311-0.0.1-SNAPSHOT.jar --spring.profiles.active=prd &

# 6. 로그 확인으로 프로세스 정상 동작 확인.
tail -f nohup.out
```

