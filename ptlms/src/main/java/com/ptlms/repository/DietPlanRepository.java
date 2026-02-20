package com.ptlms.repository;

import com.ptlms.entity.DietPlan;
import com.ptlms.entity.MealType;
import com.ptlms.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DietPlanRepository extends JpaRepository<DietPlan, Long> {
    
    List<DietPlan> findByTrainer(Trainer trainer);
    
    List<DietPlan> findByTrainerId(Long trainerId);
    
    List<DietPlan> findByMealType(MealType mealType);
    
    List<DietPlan> findByCaloriesLessThanEqual(Integer calories);
}
