package com.ptlms.repository;

import com.ptlms.entity.Trainer;
import com.ptlms.entity.WorkoutCategory;
import com.ptlms.entity.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
    
    List<WorkoutPlan> findByTrainer(Trainer trainer);
    
    List<WorkoutPlan> findByTrainerId(Long trainerId);
    
    List<WorkoutPlan> findByCategory(WorkoutCategory category);
    
    List<WorkoutPlan> findByTitleContaining(String title);
    
    List<WorkoutPlan> findByDifficultyLessThanEqual(Integer difficulty);
}
