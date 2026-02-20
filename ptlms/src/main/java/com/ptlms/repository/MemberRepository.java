package com.ptlms.repository;

import com.ptlms.entity.Member;
import com.ptlms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    // 사용자로 회원 조회
    Optional<Member> findByUser(User user);
    
    Optional<Member> findByUserId(Long userId);
    
    // ★ 핵심: 트레이너가 담당하는 회원 목록 조회
    List<Member> findByTrainer(User trainer);
    
    List<Member> findByTrainerId(Long trainerId);
    
    // 트레이너의 회원 목록 (User 정보 포함)
    @Query("SELECT m FROM Member m JOIN FETCH m.user WHERE m.trainer.id = :trainerId")
    List<Member> findByTrainerIdWithUser(@Param("trainerId") Long trainerId);
}
