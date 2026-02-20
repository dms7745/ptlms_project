package com.ptlms.repository;

import com.ptlms.entity.DietRecord;
import com.ptlms.entity.MealType;
import com.ptlms.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DietRecordRepository extends JpaRepository<DietRecord, Long> {
    
    List<DietRecord> findByMember(Member member);
    
    List<DietRecord> findByMemberId(Long memberId);
    
    List<DietRecord> findByMemberIdAndMealType(Long memberId, MealType mealType);
    
    @Query("SELECT dr FROM DietRecord dr WHERE dr.member.id = :memberId AND dr.recordedAt BETWEEN :start AND :end ORDER BY dr.recordedAt DESC")
    List<DietRecord> findByMemberIdAndDateRange(
        @Param("memberId") Long memberId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );
}
