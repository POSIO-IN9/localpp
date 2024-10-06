package edu.pnu.domain;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import edu.pnu.dto.LoginResponseDTO;
import edu.pnu.dto.SignUpDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="USERS")			// 본인 테이블명과 맞춰주어야 함
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private  Role user_type = Role.ROLE_USER;

    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    private LocalDateTime lastLoginAt;

    // 본인은 Email과 id를 동일하게 구성하기 위해 다음과 같이 작성하였다.
    public UserEntity(SignUpDTO dto) {
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.name = dto.getName();
        this.phoneNumber = dto.getPhoneNumber();
        this.user_type = dto.getUser_type() != null ? Role.valueOf(dto.getUser_type()) : Role.ROLE_USER;
        this.token = "";
        this.createdAt = LocalDateTime.now();
        this.editedAt = LocalDateTime.now();
    }
    public UserEntity(LoginResponseDTO dto) {     
        this.lastLoginAt = LocalDateTime.now();
    }


}
