package com.project.draw.controller;

import com.project.draw.dto.ApiResponse;
import com.project.draw.dto.request.LoginRequest;
import com.project.draw.dto.request.SignupRequest;
import com.project.draw.dto.response.AuthResponse;
import com.project.draw.service.impl.AuthService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<AuthResponse>> signup(
            @Valid @RequestBody SignupRequest request) {

        AuthResponse response = authService.signup(request);

        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                .code(1000)
                .message("Signup successful")
                .result(response)
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                .code(1000)
                .message("Login successful")
                .result(response)
                .build());
    }
}
