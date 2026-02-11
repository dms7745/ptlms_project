package com.ptlms.service;

import com.ptlms.entity.Trainer;
import com.ptlms.entity.User;
import com.ptlms.entity.UserRole;
import com.ptlms.repository.TrainerRepository;
import com.ptlms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Trainer createTrainer(String email, String password, String name, String phone,
                                  String specialty, Integer careerYears, String certifications, String introduction) {
        // User 생성
        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .phone(phone)
                .role(UserRole.TRAINER)
                .enabled(true)
                .build();
        user = userRepository.save(user);

        // Trainer 생성
        Trainer trainer = Trainer.builder()
                .user(user)
                .specialty(specialty)
                .careerYears(careerYears)
                .certifications(certifications)
                .introduction(introduction)
                .build();
        
        return trainerRepository.save(trainer);
    }

    public Optional<Trainer> findById(Long id) {
        return trainerRepository.findById(id);
    }

    public Optional<Trainer> findByUserId(Long userId) {
        return trainerRepository.findByUserId(userId);
    }

    public List<Trainer> findAll() {
        return trainerRepository.findAllWithUser();
    }

    public List<Trainer> findBySpecialty(String specialty) {
        return trainerRepository.findBySpecialtyContaining(specialty);
    }

    @Transactional
    public Trainer updateTrainer(Long id, String specialty, Integer careerYears, 
                                  String certifications, String introduction) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("트레이너를 찾을 수 없습니다."));
        
        trainer.setSpecialty(specialty);
        trainer.setCareerYears(careerYears);
        trainer.setCertifications(certifications);
        trainer.setIntroduction(introduction);
        
        return trainerRepository.save(trainer);
    }
}
