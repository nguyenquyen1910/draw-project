package com.project.draw.service.impl;

import com.project.draw.dto.request.CreateRoomRequest;
import com.project.draw.dto.response.CreateRoomResponse;

import java.util.UUID;

public interface RoomService {
    CreateRoomResponse createRoom(CreateRoomRequest request, UUID userId);
}
