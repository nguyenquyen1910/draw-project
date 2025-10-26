package com.project.draw.service;

import com.project.draw.dto.request.LoginRequest;
import com.project.draw.dto.response.LoginResponse;
import com.project.draw.dto.response.LogoutResponse;

import java.util.UUID;

public interface AuthService {
    LoginResponse login(LoginRequest request, UUID roomId);

    LogoutResponse logout(UUID sessionId);
}
