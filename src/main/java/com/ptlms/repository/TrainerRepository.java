package com.ptlms.repository;

import com.ptlms.entity.Trainer;
import com.ptlms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    
    Optional<Trainer> findByUser(User user);
    
    Optional<Trainer> findByUserId(Long userId);
    
    List<Trainer> findBySpecialtyContaining(String specialty);
    
    @Query("SELECT t FROM Trainer t JOIN FETCH t.user")
    List<Trainer> findAllWithUser();
}
