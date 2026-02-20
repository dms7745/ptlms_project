package com.ptlms.service;

import com.ptlms.dto.LoginRequest;
import com.ptlms.dto.LoginResponse;
import com.ptlms.dto.SignupRequest;
import com.ptlms.entity.Member;
import com.ptlms.entity.User;
import com.ptlms.entity.UserRole;
import com.ptlms.repository.MemberRepository;
import com.ptlms.repository.UserRepository;
import com.ptlms.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    // ★ 회원가입
    @Transactional
    public User signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // User 생성
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .phone(request.getPhone())
                .role(request.getRole())
                .enabled(true)
                .build();
        user = userRepository.save(user);

        // 일반 회원인 경우 Member 프로필도 생성
        if (request.getRole() == UserRole.ROLE_USER) {
            Member member = Member.builder()
                    .user(user)
                    .height(request.getHeight())
                    .weight(request.getWeight())
                    .goal(request.getGoal())
                    .build();
            memberRepository.save(member);
        }

        return user;
    }

    // ★ 로그인 (JWT 토큰 발급)
    public LoginResponse login(LoginRequest request) {
        // 인증
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // 사용자 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole().name());

        return LoginResponse.builder()
                .token(token)
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().name())
                .build();
    }
}
