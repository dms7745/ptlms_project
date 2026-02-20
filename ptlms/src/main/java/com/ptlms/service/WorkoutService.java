package com.ptlms.service;

import com.ptlms.entity.*;
import com.ptlms.repository.MemberRepository;
import com.ptlms.repository.TrainerRepository;
import com.ptlms.repository.WorkoutPlanRepository;
import com.ptlms.repository.WorkoutRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkoutService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final WorkoutRecordRepository workoutRecordRepository;
    private final TrainerRepository trainerRepository;
    private final MemberRepository memberRepository;

    // ========== WorkoutPlan ==========
    
    @Transactional
    public WorkoutPlan createWorkoutPlan(Long trainerId, String title, String description,
                                          WorkoutCategory category, Integer difficulty,
                                          Integer durationMinutes, Integer calories) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("트레이너를 찾을 수 없습니다."));
        
        WorkoutPlan plan = WorkoutPlan.builder()
                .trainer(trainer)
                .title(title)
                .description(description)
                .category(category)
                .difficulty(difficulty)
                .durationMinutes(durationMinutes)
                .calories(calories)
                .build();
        
        return workoutPlanRepository.save(plan);
    }

    public List<WorkoutPlan> findAllPlans() {
        return workoutPlanRepository.findAll();
    }

    public List<WorkoutPlan> findPlansByTrainerId(Long trainerId) {
        return workoutPlanRepository.findByTrainerId(trainerId);
    }

    public List<WorkoutPlan> findPlansByCategory(WorkoutCategory category) {
        return workoutPlanRepository.findByCategory(category);
    }

    public Optional<WorkoutPlan> findPlanById(Long id) {
        return workoutPlanRepository.findById(id);
    }

    // ========== WorkoutRecord ==========
    
    @Transactional
    public WorkoutRecord createWorkoutRecord(Long memberId, Long planId, String memberFeedback) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        WorkoutPlan plan = workoutPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("운동 플랜을 찾을 수 없습니다."));
        
        WorkoutRecord record = WorkoutRecord.builder()
                .member(member)
                .workoutPlan(plan)
                .completed(false)
                .memberFeedback(memberFeedback)
                .build();
        
        return workoutRecordRepository.save(record);
    }

    @Transactional
    public WorkoutRecord completeWorkout(Long recordId, Integer actualDuration, Integer intensityRating) {
        WorkoutRecord record = workoutRecordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("운동 기록을 찾을 수 없습니다."));
        
        record.setCompleted(true);
        record.setCompletedAt(LocalDateTime.now());
        record.setActualDuration(actualDuration);
        record.setIntensityRating(intensityRating);
        
        return workoutRecordRepository.save(record);
    }

    public List<WorkoutRecord> findRecordsByMemberId(Long memberId) {
        return workoutRecordRepository.findByMemberId(memberId);
    }

    @Transactional
    public WorkoutRecord addTrainerFeedback(Long recordId, String feedback) {
        WorkoutRecord record = workoutRecordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("운동 기록을 찾을 수 없습니다."));
        
        record.setTrainerFeedback(feedback);
        return workoutRecordRepository.save(record);
    }
}
