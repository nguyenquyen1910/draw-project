package com.project.draw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.draw.dto.ApiResponse;
import com.project.draw.dto.response.UserReponse;
import com.project.draw.service.impl.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping("/me")
    public ApiResponse<UserReponse> getCurrentUser() {
        return ApiResponse.<UserReponse>builder()
                .code(1000)
                .message("Get current user successful")
                .result(userService.getCurrentUser())
                .build();
    }
}
