# SNS 웹 피드 통합 서비스

## 개요
* 본 서비스는 다양한 SNS 플랫폼에 게시된 웹 피드를 한 눈에 파악할 수 있도록 통합합니다.

### 주요 기능
1. 사용자는 인스타그램, 페이스북, 스레드, 트위터 등, 다양한 SNS 플랫폼의 게시물 중 특정 해시태그가 달린 것들을 단번에 조회할 수 있습니다.
2. 특정 해시태그가 달린 게시물의 총 조회 수, 총 게시물 수, 총 좋아요 수 등 다양한 유형의 통계 정보를 제공합니다.

### 편의성
1. 해시태그가 달린 게시물을 조회하려고 각 플랫폼을 직접 들러야 했던 번거로움을 없애고, 빠르고 쉽게 게시물을 조회할 수 있게 됩니다.
2. 게시물의 관심도를 수치적으로 확인할 수 있습니다.

<br>

## 목차
* [개요](#개요)
* [기술 스택](#기술-스택)
* [패키지 구조](#패키지-구조)
* [프로젝트 주제](#프로젝트-주제)
* [기능 목록](#기능-목록)
* [DB 설계](#DB-설계)
* [저자](#저자)

<br>

## 기술 스택
![SPRING BOOT](https://img.shields.io/badge/spring_boot-6DB33F?style=for-the-badge&logoColor=ffffff)
![SPRING DATA JPA](https://img.shields.io/badge/spring_data_jpa-6DB33F?style=for-the-badge&logoColor=ffffff)
![JAVA](https://img.shields.io/badge/java-007396?style=for-the-badge)
![H2 database engine](https://img.shields.io/badge/h2_database-004088?style=for-the-badge&logoColor=white)

<br>

## 패키지 구조

<details>
<summary> 클릭하여 펼치기 </summary>

```
src
├─main
│  ├─java
│  │  └─com
│  │     └─wanted
│  │        └─sns
│  │           ├─config
│  │           ├─controller
│  │           ├─domain
│  │           ├─dto
│  │           ├─exception
│  │           ├─repository
│  │           ├─service
│  │           └─support
│  └─resources
│     └─h2
└─test
   ├─java
   │  └─com
   │     └─wanted
   │        └─sns
   │           ├─acceptance
   │           ├─domain
   │           ├─post
   │           ├─repository
   │           ├─service
   │           └─support
   └─resources  
```

</details>

<br>

## 프로젝트 주제
* 스프링 프레임워크를 활용한 REST API 개발
* JWT 로그인
* 관계형 데이터베이스 설계

<br>

## 기능 목록
### 사용자 관련 기능
* **회원 권한 구분**
    - 회원의 역할을 게스트(GUEST)와 멤버(MEMBER)으로 나눔
    - 가입 전엔 게스트 권한, 로그인 후엔 멤버 권한을 가짐


* **회원 가입 및 승인**
    1. 인증코드 생성
        + 이미 생성된 인증코드가 있다면, 삭제하고 새로 생성
    2. 인증코드 확인
        + 인증코드 확인 결과는 true, false 로 반환
        + 인증코드가 일치할 경우, 회원 역할을 멤버로 변경하고 인증코드 삭제
        + 인증코드가 일치하지 않을 경우, 인증 시도 횟수 증가


### 게시물 관련 기능
* **게시물 목록 조회**
    - SNS(페이스북, 인스타그램, 쓰레드, 트위터)에 등록된 모든 게시물의 목록을 조회
    - 페이지당 10건씩 목록 반환
    - 해시태그, SNS 타입, 제목, 내용, 제목 + 내용으로 검색 수행
    - 게시물 목록을 생성일, 수정일, 좋아요 수, 공유 수, 조회 수에 따라 조회
    - 게시물 목록 조회 시 사용자에게 아래와 같은 정보를 전달
        + 게시물 id, SNS 타입, 작성자 정보, 게시물 제목, 게시물 내용(최대 20자)
        + 해시태그, 조회 수, 좋아요 수, 공유 횟수, 게시물 수정일자, 게시물 생성일자


* **게시물 상세 조회**
    - 게시물 목록 조회 시 사용자에게 아래와 같은 정보를 전달
        - 게시물 id, SNS 타입, 작성자 정보, 게시물 제목, 게시물 전체 내용
        - 해시태그, 조회 수, 좋아요 수, 공유 횟수, 게시물 수정일자, 게시물 생성일자


* **게시물 좋아요**
    - 사용자가 게시물에 좋아요를 누를 수 있음
    - 게시물의 좋아요 수를 증가시킴


* **게시물 통계 조회**
    - 사용자는 다음과 같은 필터를 사용하여 통계를 조회할 수 있다.
        + 해시태그, 조회기간 타입(날짜 또는 날짜-시간 형식)
        + 조회 시작일, 조회 종료일
        + 조회하고자 하는 통계 데이터 타입: 게시물 수, 조회 수, 좋아요 수, 공유 횟수


## DB 설계
### 멤버 테이블
<details>
<summary>클릭하여 상세보기</summary>

| Name         | Type    | NotNull | Default | Description | Primary Key |
|--------------|---------|---------|---------|-------------|-------------|
| seq          | BIGINT  | O       |         | 고유번호        | O           |
| id           | VARCHAR | O       |         | 계정          |             |
| email        | VARCHAR | O       |         | 이메일         |             |
| password     | VARCHAR | O       |         | 비밀번호        |             |
| birthday     | DATE    | O       |         | 생년월일        |             |
| name         | VARCHAR | O       |         | 실명          |             |
| phone_number | VARCHAR | O       |         | 전화번호        |             |
| created_at   | DATE    | O       |         | 생성일         |             |

</details>

### 포스트 테이블
<details>
<summary>클릭하여 상세보기</summary>

| Name        | Type    | NotNull | Default | Description | Primary Key |
|-------------|---------|---------|---------|-------------|-------------|
| seq         | BIGINT  | O       |         | 고유번호        | O           |
| type        | VARCHAR | O       |         | SNS 타입      |             |
| title       | VARCHAR | O       |         | 제목          |             |
| content     | VARCHAR | O       |         | 내용          |             |
| view_count  | INT     | O       |         | 조회수         |             |
| like_count  | INT     | O       |         | 좋아요수        |             |
| share_count | INT     | O       |         | 공유수         |             |
| created_at  | DATE    | O       |         | 작성일         |             |
| updated_at  | DATE    | O       |         | 수정일         |             |

</details>

### 인증코드 테이블
<details>
<summary>클릭하여 상세보기</summary>

| Name     | Type    | NotNull | Default | Description | Primary Key |
|----------|---------|---------|---------|-------------|-------------|
| seq      | BIGINT  | O       |         | 고유번호        | O           |
| seq_user | BIGINT  | O       |         | 유저 고유번호     |             |
| code     | VARCHAR | O       |         | 인증 번호       |             |
| count    | INT     | O       | 0       | 인증 횟수       |             |

</details>

### 해시코드 매핑 테이블
<details>
<summary>클릭하여 상세보기</summary>

| Name        | Type    | NotNull | Default | Description | Primary Key |
|-------------|---------|---------|---------|-------------|-------------|
| seq         | BIGINT  | O       |         | 고유번호        | O           |
| seq_user    | BIGINT  | O       |         | 유저 고유번호     |             |
| seq_hashtag | BIGINT  | O       |         | 해시태그 고유번호   |             |

</details>

### 해시코드 매핑 테이블
<details>
<summary>클릭하여 상세보기</summary>

| Name | Type    | NotNull | Default | Description | Primary Key |
|------|---------|---------|---------|-------------|-------------|
| seq  | BIGINT  | O       |         | 고유번호        | O           |
| name | VARCHAR | O       |         | 해시태그 이름     |             |

</details>
<br>

## 저자

<a href="https://github.com/neppiness">김정현</a>,
<a href="https://github.com/irerin07">민경수</a>,
<a href="https://github.com/unknownKade">박보현</a>,
<a href="https://github.com/hanull">이한울</a>
(클릭 시 깃허브로 이동)