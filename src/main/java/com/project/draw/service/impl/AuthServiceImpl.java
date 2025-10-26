package com.project.draw.service.impl;

import com.project.draw.dto.request.LoginRequest;
import com.project.draw.dto.response.LoginResponse;
import com.project.draw.dto.response.LogoutResponse;
import com.project.draw.entity.User;
import com.project.draw.entity.UserSession;
import com.project.draw.repository.UserRepository;
import com.project.draw.repository.UserSessionRepository;
import com.project.draw.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserSessionRepository userSessionRepository;

    @Override
    public LoginResponse login(LoginRequest request, UUID roomId) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Username not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        UserSession session = UserSession.builder()
                .user(user)
                .room(null) // set room nếu cần
                .joinedAt(LocalDateTime.now())
                .status(UserSession.SessionStatus.ONLINE)
                .build();

        userSessionRepository.save(session);

        String token = session.getId().toString(); // có thể dùng sessionId làm token

        return new LoginResponse(token, user.getUsername());
    }

    @Override
    public LogoutResponse logout(UUID sessionId) {
        UserSession session = userSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        session.setStatus(UserSession.SessionStatus.OFFLINE);
        session.setLeftAt(LocalDateTime.now());

        userSessionRepository.save(session);

        return new LogoutResponse("Logout successfully");
    }
}
