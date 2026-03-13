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
User
 │
 ├─ Trainer
 │     └─ WorkoutPlan
 │     └─ DietPlan
 │
 └─ Member
       └─ WorkoutRecord
       └─ DietRecord
       └─ BodyRecord
```
## 6. React 프로젝트 구조
React 기반 프론트엔드는 다음과 같은 구조로 설계.

```
/opt/ptlms-frontend/
├── src/
│   ├── components/     # 재사용 컴포넌트
│   │   ├── Layout/     # Navbar, Sidebar, ProtectedRoute
│   │   └── Common/     # Loading, Modal
│   │
│   ├── pages/          # 페이지 컴포넌트
│   │   ├── Home.js
│   │   ├── Login.js
│   │   ├── Register.js
│   │   ├── Dashboard.js
│   │   ├── Members.js
│   │   └── Schedule.js
│   │
│   ├── store/          # Redux 상태 관리
│   │   ├── index.js
│   │   ├── authSlice.js
│   │   └── memberSlice.js
│   │
│   ├── services/       # Axios API 서비스
│   │   └── api.js
│   │
│   └── styles/         # CSS 스타일
│       └── global.css
```

## 7. 상태 관리 (Redux)
Redux를 사용하여 전역 상태를 관리.

authSlice
- 로그인 / 로그아웃 관리<br>
- JWT 토큰 저장 및 인증 상태 관리<br>

memberSlice
- 회원 목록 상태 관리<br>
- 트레이너가 관리하는 회원 데이터 처리<br>

## 8. API 통신 (Axios)
Spring Boot API와 통신하기 위해 Axios를 사용.

주요 특징
- JWT 토큰 자동 첨부<br>
- Axios Interceptor로 인증 처리<br>
- 401 Unauthorized 발생 시 자동 로그아웃 처리<br>

## 9. 주요 기능 구현
- 로그인 / 회원가입<br>
- 보호된 라우트 (ProtectedRoute)<br>
- 대시보드<br>
- 회원 관리<br>
- PT 일정 캘린더<br>

## 10. 기술 스택 (Tech Stack)
- Backend<br>
Java (Spring Boot)
Spring Security
JWT
JPA (Hibernate)

- Frontend<br>
React
Redux
Axios

- Database<br>
MySQL / H2 Database

- Development Tool<br>
IntelliJ
STS4 (Spring Tool Suite 4)

- Collaboration<br>
GitHub
Notion
# http://localhost:8082
```
