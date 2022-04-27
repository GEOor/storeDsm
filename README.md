# storeDsm

DSM 파일을 DB에 저장하는 코드

## 1️⃣ Fork 받기 (또는 clone 하기) 

- 해당 repository를 fork 하여 local로 clone 합니다.  


## 2️⃣ PostgreSQL 준비 (pgAdmin 사용) 

- PostgreSQL 설치
- pgAdmin의 Querytool 사용 -> create Database geor (geor DB 생성)
- geor DB에 DSM 테이블 생성 -> create table DSM

![image](https://user-images.githubusercontent.com/65909160/165447892-b745850b-fee4-4b9f-8366-e9a20768000d.png)

## 3️⃣ Files 폴더에 DSM 파일 넣기 (여러 개 가능)

- Github에는 DSM 파일 용량이 너무 커서 못올리지만 개인적으로 가지고 있는 DSM 파일을 넣으면 됨

## 4️⃣ Main 실행

- 실행 시 Files 폴더에 있는 모든 DSM 파일을 읽는다.
- DSM 파일을 하나 씩 읽으면서 100만 단위로 분할해서 DB connection을 열고 저장한다 (HEAP OverFlow 방지)
- DSM 파일 하나(약 500MB)를 DB에 넣을 때 까지 총 316543ms (= 316s = 5m) 걸린다.

- select * from dsm 실행 화면

![image](https://user-images.githubusercontent.com/65909160/165452061-6e5da576-a9e2-4a13-b96f-06996b93e5a2.png)


- select count(*) from dsm 실행 화면

![image](https://user-images.githubusercontent.com/65909160/165452161-3cf2900a-7c4c-4903-b74c-2cc866453c3c.png)
