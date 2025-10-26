package com.project.draw.service;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.project.draw.dto.response.UserReponse;
import com.project.draw.entity.User;
import com.project.draw.exception.ApplicationException;
import com.project.draw.exception.ErrorCode;
import com.project.draw.repository.UserRepository;
import com.project.draw.service.impl.UserService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    
    @Override
    public UserReponse getCurrentUser() {
        var context = SecurityContextHolder.getContext();
        UUID userId = UUID.fromString(context.getAuthentication().getName());

        User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        return UserReponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .build();
    }
}
