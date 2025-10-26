package com.project.draw.controller;

import com.project.draw.dto.ApiResponse;
import com.project.draw.dto.request.CreateRoomRequest;
import com.project.draw.dto.response.RoomResponse;
import com.project.draw.service.impl.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RoomController {
    RoomService roomService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoomResponse>> createRoom(
            @Valid @RequestBody CreateRoomRequest request,
            Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID userId = UUID.fromString(jwt.getSubject());
        RoomResponse response = roomService.createRoom(request, userId);
        return ResponseEntity.ok(ApiResponse.<RoomResponse>builder()
                .code(1000)
                .message("Create room successful")
                .result(response)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<RoomResponse>>> getAllRooms(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<RoomResponse> rooms = roomService.getAllRooms(page, size);
        return ResponseEntity.ok(ApiResponse.<Page<RoomResponse>>builder()
                .code(1000)
                .message("Get all rooms successful")
                .result(rooms)
                .build());
    }
    
}
