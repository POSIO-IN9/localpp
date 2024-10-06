package edu.pnu.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {
    private String email;
    private String name;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
    private String user_type;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    private LocalDateTime lastLoginAt;
    private String token;
}