package com.ptlms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "workout_records")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_plan_id")
    private WorkoutPlan workoutPlan;

    private boolean completed;

    private LocalDateTime completedAt;

    private Integer actualDuration;

    @Column(columnDefinition = "TEXT")
    private String memberFeedback;

    @Column(columnDefinition = "TEXT")
    private String trainerFeedback;

    private Integer intensityRating;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
