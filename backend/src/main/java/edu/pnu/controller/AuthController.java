package edu.pnu.controller;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.dto.LoginDTO;
import edu.pnu.dto.ResponseDTO;
import edu.pnu.dto.SignUpDTO;
import edu.pnu.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseDTO<?> signUp(@RequestBody SignUpDTO requestBody) {
        return authService.signUp(requestBody);
    }
    @PostMapping("/signin")
    public ResponseEntity<ResponseDTO<?>> login(@RequestBody LoginDTO requestBody, HttpServletResponse response) {
        ResponseDTO<?> result = authService.login(requestBody,response);
        return ResponseEntity.ok(result);
    }
}