package com.ptlms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diet_plans")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DietPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private MealType mealType;

    @Column(columnDefinition = "TEXT")
    private String menu;

    private Integer calories;

    private Double protein;

    private Double carbohydrate;

    private Double fat;

    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String recipe;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "dietPlan", fetch = FetchType.LAZY)
    @Builder.Default
    private List<DietRecord> dietRecords = new ArrayList<>();

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
