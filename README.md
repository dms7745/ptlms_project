# 🏋️ PT LMS - 운동 PT 관리 시스템
본 프로젝트는 트레이너와 회원 간의 운동 및 식단 관리를 효율적으로 지원하기 위해 설계된 PT 관리 시스템입니다.<br>
회원의 운동 기록과 신체 데이터를 체계적으로 관리하여 개인 맞춤형 트레이닝 환경을 제공하는 것을 목표로 합니다.

## URL https://ptlms.da2un.store

## 1. 프로젝트 개요
- 개발 기간: 2025.10.15 ~ 2025.12.31<br>
- 주요 목적: 트레이너와 회원 간 운동 및 식단 관리 데이터를 디지털화하여 효율적인 관리 환경 구축<br>
- 활용 방안 및 기대 효과 : 회원별 운동 기록 관리, 개인 맞춤형 트레이닝 제공, 트레이너의 회원 관리 효율 향상<br>

## 2. 주요 기능
- 회원 관리: 회원가입, 로그인, 마이페이지를 통한 회원 정보 관리<br>
- 운동 관리: 운동 프로그램 생성 및 운동 기록 관리 기능<br>
- 식단 관리: 트레이너 식단 가이드 제공 및 회원 식단 기록 저장<br>
- 신체 기록 관리: 체중, 체지방, 근육량 등의 신체 변화 기록 관리<br>

## 3. 주요 페이지 소개
1. 로그인 / 회원가입<br>
   회원과 트레이너 계정으로 가입 가능
   
   img

2. 트레이너 대시보드
   트레이너는 다음 기능을 사용 가능.
   
   img
   
  - 회원 목록 조회
  - 회원 운동 기록 관리
  - 운동 프로그램 생성
  - 식단 가이드 작성

3. 회원 마이페이지
   회원은 다음 기능을 사용 가능.

    img
   
  - 오늘 운동 루틴 확인
  - 운동 기록 등록
  - 식단 기록 업로드
  - 신체 변화 기록 확인

4. PT 일정 관리
   트레이너와 회원 간의 PT 일정을 캘린더 형태로 관리 가능.


## 4. 시스템 아키텍처
본 프로젝트는 React와 Spring Boot가 분리된 REST API 기반 구조로 설계.

```
React (Frontend)
      ↓
Axios API 요청
      ↓
Spring Boot (REST API)
      ↓
Service Layer
      ↓
JPA Repository
      ↓
Database
```

React는 사용자 인터페이스를 담당하고,
Spring Boot는 REST API 서버로서 데이터 처리와 비즈니스 로직을 담당.

## 5. 데이터베이스 설계 (ERD)
회원(User), 트레이너(Trainer), 운동 기록(WorkoutLog) 간의 관계를 고려하여 정규화된 DB 구조로 설계.

```
User ──1:1── Member ──N:1── Trainer
                │
               1:N
                ▼
           WorkoutLog
```

```java
// 트레이너가 자기 회원들만 조회
@ManyToOne
private User trainer;

// 회원이 본인 기록만 조회  
@ManyToOne
private Member member;
```

---

## 🔐 Security 설정 (핵심)

```java
.requestMatchers("/api/auth/**").permitAll()           // 로그인/회원가입
.requestMatchers("/api/trainer/**").hasRole("TRAINER") // 트레이너만
.requestMatchers("/api/member/**").hasRole("USER")     // 회원만
```

---

## 📁 구조

```
src/main/java/com/ptlms/
├── entity/      # User, Member, WorkoutLog
├── repository/  # JPA Repository
├── service/     # 비즈니스 로직
├── controller/  # REST API
├── security/    # JWT 인증
└── config/      # Security 설정
```

---
## 4. 데이터베이스 설계 (ERD)
사용자(User), 강의(Course), 게시글(Post), 학습기록(Progress) 간의 관계를 고려한 정규화된 DB 설계 적용 (이부분 수정)


```
src/main/java/com/ptlms/
├── entity/      # User, Member, WorkoutLog
├── repository/  # JPA Repository
├── service/     # 비즈니스 로직
├── controller/  # REST API
├── security/    # JWT 인증
└── config/      # Security 설정
```


## �� 실행

```bash
./gradlew bootRun
# http://localhost:8082
```
