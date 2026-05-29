package com.example.HospitalManaSystem.Controller;

import com.example.HospitalManaSystem.Security.AuthService;
import com.example.HospitalManaSystem.dto.LoginRequestDto;
import com.example.HospitalManaSystem.dto.LoginResposeDto;
import com.example.HospitalManaSystem.dto.SignUpRequestDto;
import com.example.HospitalManaSystem.dto.SignUpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResposeDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signUp")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        return ResponseEntity.ok(authService.signUp(signUpRequestDto));
    }

}
