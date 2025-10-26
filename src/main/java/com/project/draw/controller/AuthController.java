package com.project.draw.controller;

import com.project.draw.dto.request.LoginRequest;
import com.project.draw.dto.response.LoginResponse;
import com.project.draw.dto.response.LogoutResponse;
import com.project.draw.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request,
                               @RequestParam(required = false) UUID roomId) {
        return authService.login(request, roomId);
    }

    @PostMapping("/logout")
    public LogoutResponse logout(@RequestParam UUID sessionId) {
        return authService.logout(sessionId);
    }
}
