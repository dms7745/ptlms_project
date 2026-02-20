package com.ptlms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "workout_logs")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ★ 핵심: 회원과 N:1 관계
    // member.getWorkoutLogs()로 본인 기록만 조회 가능
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 운동 날짜
    @Column(nullable = false)
    private LocalDate workoutDate;

    // 운동 부위 (가슴, 등, 하체 등)
    @Enumerated(EnumType.STRING)
    private WorkoutPart workoutPart;

    // 운동 내용 (상세 설명)
    @Column(columnDefinition = "TEXT")
    private String workoutDetail;

    // 세트수
    private Integer sets;

    // 운동 시간 (분)
    private Integer durationMinutes;

    // 식단 사진 경로
    private String dietPhotoUrl;

    // 식단 메모
    @Column(columnDefinition = "TEXT")
    private String dietMemo;

    // 오운완(오늘 운동 완료) 여부
    private boolean completed;

    // 트레이너 피드백
    @Column(columnDefinition = "TEXT")
    private String trainerFeedback;

    // 생성일
    private LocalDateTime createdAt;

    // 수정일
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
