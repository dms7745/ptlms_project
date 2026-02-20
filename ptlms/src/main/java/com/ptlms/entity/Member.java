package com.ptlms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User와 1:1 관계 (회원 정보)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // ★ 핵심: 담당 트레이너 (N:1)
    // "트레이너가 자기 회원들의 목록만 조회" 가능
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private User trainer;

    // 운동 목표
    private String goal;

    // 신체 정보
    private Double height;
    private Double weight;
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    // PT 정보
    private LocalDate ptStartDate;
    private LocalDate ptEndDate;
    private Integer totalSessions;      // 총 PT 횟수
    private Integer remainingSessions;  // 남은 PT 횟수

    // ★ 핵심: 본인의 운동 기록들 (1:N)
    // "회원이 본인의 지난 기록만 조회" 가능
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<WorkoutLog> workoutLogs = new ArrayList<>();
}
