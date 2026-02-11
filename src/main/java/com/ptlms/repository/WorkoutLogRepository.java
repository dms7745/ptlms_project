package com.ptlms.repository;

import com.ptlms.entity.Member;
import com.ptlms.entity.WorkoutLog;
import com.ptlms.entity.WorkoutPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {
    
    // ★ 핵심: 회원의 운동 기록 조회 (본인 기록만)
    List<WorkoutLog> findByMember(Member member);
    
    List<WorkoutLog> findByMemberId(Long memberId);
    
    // 날짜별 운동 기록
    List<WorkoutLog> findByMemberIdAndWorkoutDate(Long memberId, LocalDate workoutDate);
    
    // 기간별 운동 기록 (달력 표시용)
    @Query("SELECT w FROM WorkoutLog w WHERE w.member.id = :memberId AND w.workoutDate BETWEEN :start AND :end ORDER BY w.workoutDate DESC")
    List<WorkoutLog> findByMemberIdAndDateRange(
        @Param("memberId") Long memberId,
        @Param("start") LocalDate start,
        @Param("end") LocalDate end
    );
    
    // 오운완(완료된 운동) 기록
    List<WorkoutLog> findByMemberIdAndCompleted(Long memberId, boolean completed);
    
    // 운동 부위별 기록
    List<WorkoutLog> findByMemberIdAndWorkoutPart(Long memberId, WorkoutPart workoutPart);
    
    // 트레이너가 담당 회원들의 최근 기록 조회
    @Query("SELECT w FROM WorkoutLog w WHERE w.member.trainer.id = :trainerId ORDER BY w.createdAt DESC")
    List<WorkoutLog> findRecentByTrainerId(@Param("trainerId") Long trainerId);
}
