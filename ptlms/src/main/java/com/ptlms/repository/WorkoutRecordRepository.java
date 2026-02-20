package com.ptlms.repository;

import com.ptlms.entity.Member;
import com.ptlms.entity.WorkoutRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkoutRecordRepository extends JpaRepository<WorkoutRecord, Long> {
    
    List<WorkoutRecord> findByMember(Member member);
    
    List<WorkoutRecord> findByMemberId(Long memberId);
    
    List<WorkoutRecord> findByMemberIdAndCompleted(Long memberId, boolean completed);
    
    @Query("SELECT wr FROM WorkoutRecord wr WHERE wr.member.id = :memberId AND wr.createdAt BETWEEN :start AND :end")
    List<WorkoutRecord> findByMemberIdAndDateRange(
        @Param("memberId") Long memberId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );
}
