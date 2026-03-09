# 🏋️ PT LMS - 운동 PT 관리 시스템

> **JPA + Spring Security + JWT** 기반의 운동 PT 관리 시스템

## URL https://ptlms.da2un.store

## 📋 프로젝트 개요

트레이너와 회원 간의 운동/식단 관리를 위한 웹 애플리케이션

### 🎯 핵심 목표
- **JPA**: 복잡한 데이터 관계를 객체 지향적으로 처리
- **Security**: 철저한 권한 관리 (트레이너/회원 분리)

---

## 🛠 기술 스택

| Backend | Spring Boot 3.2, JPA, Spring Security, JWT |
|---------|---------------------------------------------|
| Database | H2 (개발) / MySQL (운영) |
| Frontend | React (예정) |

---

## 📊 JPA 설계 (핵심)

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

## �� 실행

```bash
./gradlew bootRun
# http://localhost:8082
```
