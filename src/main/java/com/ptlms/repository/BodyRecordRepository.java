package com.ptlms.repository;

import com.ptlms.entity.BodyRecord;
import com.ptlms.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BodyRecordRepository extends JpaRepository<BodyRecord, Long> {
    
    List<BodyRecord> findByMember(Member member);
    
    List<BodyRecord> findByMemberId(Long memberId);
    
    List<BodyRecord> findByMemberIdOrderByMeasureDateDesc(Long memberId);
    
    Optional<BodyRecord> findFirstByMemberIdOrderByMeasureDateDesc(Long memberId);
    
    @Query("SELECT br FROM BodyRecord br WHERE br.member.id = :memberId AND br.measureDate BETWEEN :start AND :end")
    List<BodyRecord> findByMemberIdAndDateRange(
        @Param("memberId") Long memberId,
        @Param("start") LocalDate start,
        @Param("end") LocalDate end
    );
}
