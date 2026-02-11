package com.ptlms.repository;

import com.ptlms.entity.User;
import com.ptlms.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    // 트레이너 목록 조회
    List<User> findByRole(UserRole role);
}
