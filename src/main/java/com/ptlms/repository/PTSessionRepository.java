package com.ptlms.repository;

import com.ptlms.entity.Member;
import com.ptlms.entity.PTSession;
import com.ptlms.entity.PTStatus;
import com.ptlms.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PTSessionRepository extends JpaRepository<PTSession, Long> {
    
    List<PTSession> findByMember(Member member);
    
    List<PTSession> findByTrainer(Trainer trainer);
    
    List<PTSession> findByMemberIdAndStatus(Long memberId, PTStatus status);
    
    List<PTSession> findByTrainerIdAndStatus(Long trainerId, PTStatus status);
    
    @Query("SELECT ps FROM PTSession ps WHERE ps.trainer.id = :trainerId AND ps.scheduledAt BETWEEN :start AND :end ORDER BY ps.scheduledAt")
    List<PTSession> findByTrainerIdAndDateRange(
        @Param("trainerId") Long trainerId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );
    
    @Query("SELECT ps FROM PTSession ps WHERE ps.member.id = :memberId AND ps.scheduledAt BETWEEN :start AND :end ORDER BY ps.scheduledAt")
    List<PTSession> findByMemberIdAndDateRange(
        @Param("memberId") Long memberId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );
}
