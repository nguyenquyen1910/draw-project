package com.project.draw.service.impl;

import com.project.draw.dto.request.CreateRoomRequest;
import com.project.draw.dto.response.RoomResponse;

import java.util.UUID;

import org.springframework.data.domain.Page;

public interface RoomService {
    RoomResponse createRoom(CreateRoomRequest request, UUID userId);
    Page<RoomResponse> getAllRooms(int page, int size);
}
