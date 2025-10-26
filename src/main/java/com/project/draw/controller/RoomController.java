package com.project.draw.controller;

import com.project.draw.dto.ApiResponse;
import com.project.draw.dto.request.CreateRoomRequest;
import com.project.draw.dto.response.CreateRoomResponse;
import com.project.draw.service.impl.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RoomController {
    RoomService roomService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateRoomResponse>> createRoom(
            @Valid @RequestBody CreateRoomRequest request,
            Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID userId = UUID.fromString(jwt.getSubject());
        CreateRoomResponse response = roomService.createRoom(request, userId);
        return ResponseEntity.ok(ApiResponse.<CreateRoomResponse>builder()
                .code(1000)
                .message("Create room successful")
                .result(response)
                .build());
    }
}
