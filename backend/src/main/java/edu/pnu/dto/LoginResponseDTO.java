package edu.pnu.dto;

import edu.pnu.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private int exprTime;
    private UserEntity user;
}
