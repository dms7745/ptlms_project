package com.ptlms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "body_records")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BodyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private LocalDate measureDate;

    private Double weight;

    private Double muscleMass;

    private Double bodyFatPercentage;

    private Double bodyFatMass;

    private Double bmi;

    private Integer basalMetabolicRate;

    private Double waistCircumference;

    private String inbodyImageUrl;

    @Column(columnDefinition = "TEXT")
    private String memo;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (measureDate == null) {
            measureDate = LocalDate.now();
        }
    }
}
