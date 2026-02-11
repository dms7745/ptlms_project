package com.ptlms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trainers")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String specialty;

    private Integer careerYears;

    private String certifications;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    private String profileImage;

    @OneToMany(mappedBy = "trainer", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "trainer", fetch = FetchType.LAZY)
    @Builder.Default
    private List<WorkoutPlan> workoutPlans = new ArrayList<>();

    @OneToMany(mappedBy = "trainer", fetch = FetchType.LAZY)
    @Builder.Default
    private List<DietPlan> dietPlans = new ArrayList<>();
}
