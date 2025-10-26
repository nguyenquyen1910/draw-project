package com.project.draw.service.impl;

import com.project.draw.dto.request.LoginRequest;
import com.project.draw.dto.request.SignupRequest;
import com.project.draw.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse signup(SignupRequest request);
}
