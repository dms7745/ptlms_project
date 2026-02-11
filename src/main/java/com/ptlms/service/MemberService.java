package com.ptlms.service;

import com.ptlms.entity.*;
import com.ptlms.repository.MemberRepository;
import com.ptlms.repository.TrainerRepository;
import com.ptlms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member createMember(String email, String password, String name, String phone,
                                Double height, Double weight, LocalDate birthDate, Gender gender, String goal) {
        // User 생성
        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .phone(phone)
                .role(UserRole.MEMBER)
                .enabled(true)
                .build();
        user = userRepository.save(user);

        // Member 생성
        Member member = Member.builder()
                .user(user)
                .height(height)
                .weight(weight)
                .birthDate(birthDate)
                .gender(gender)
                .goal(goal)
                .registrationDate(LocalDate.now())
                .build();
        
        return memberRepository.save(member);
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Optional<Member> findByUserId(Long userId) {
        return memberRepository.findByUserId(userId);
    }

    public List<Member> findByTrainerId(Long trainerId) {
        return memberRepository.findByTrainerIdWithUser(trainerId);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Transactional
    public Member assignTrainer(Long memberId, Long trainerId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("트레이너를 찾을 수 없습니다."));
        
        member.setTrainer(trainer);
        return memberRepository.save(member);
    }

    @Transactional
    public Member updateMember(Long id, Double height, Double weight, String goal, Integer remainingSessions) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        
        if (height != null) member.setHeight(height);
        if (weight != null) member.setWeight(weight);
        if (goal != null) member.setGoal(goal);
        if (remainingSessions != null) member.setRemainingSessions(remainingSessions);
        
        return memberRepository.save(member);
    }
}
