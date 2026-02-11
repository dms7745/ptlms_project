package com.ptlms.dto;

import com.ptlms.entity.UserRole;
import lombok.Data;

@Data
public class SignupRequest {
    private String email;
    private String password;
    private String name;
    private String phone;
    private UserRole role;  // ROLE_USER or ROLE_TRAINER
    
    // 회원 전용 필드
    private Double height;
    private Double weight;
    private String goal;
}
