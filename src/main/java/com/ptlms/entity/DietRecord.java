package com.ptlms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "diet_records")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DietRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_plan_id")
    private DietPlan dietPlan;

    @Enumerated(EnumType.STRING)
    private MealType mealType;

    @Column(columnDefinition = "TEXT")
    private String actualMenu;

    private Integer calories;

    private String photoUrl;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @Column(columnDefinition = "TEXT")
    private String trainerFeedback;

    private LocalDateTime recordedAt;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (recordedAt == null) {
            recordedAt = LocalDateTime.now();
        }
    }
}
