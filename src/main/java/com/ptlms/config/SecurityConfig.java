package com.ptlms.config;

import com.ptlms.security.JwtAuthenticationFilter;
import com.ptlms.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CORS 설정
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // CSRF 비활성화 (JWT 사용)
            .csrf(csrf -> csrf.disable())
            
            // 세션 사용 안 함 (JWT 사용)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // ★ 핵심: 권한별 접근 제어
            .authorizeHttpRequests(auth -> auth
                // permitAll(): 홈 화면, 로그인, 회원가입
                .requestMatchers("/", "/api/auth/**", "/api/public/**").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                
                // hasRole('TRAINER'): 식단 가이드 작성, 회원 운동 기록 수정
                .requestMatchers("/api/trainer/**").hasRole("TRAINER")
                .requestMatchers("/api/workout/feedback/**").hasRole("TRAINER")
                .requestMatchers("/api/diet/guide/**").hasRole("TRAINER")
                
                // hasRole('USER'): 본인 기록 조회, 오운완 등록
                .requestMatchers("/api/member/**").hasRole("USER")
                .requestMatchers("/api/workout/my/**").hasRole("USER")
                .requestMatchers("/api/workout/complete/**").hasRole("USER")
                
                // 나머지는 인증 필요
                .anyRequest().authenticated()
            )
            
            // JWT 필터 추가
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), 
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://ptlms.da2un.store"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
