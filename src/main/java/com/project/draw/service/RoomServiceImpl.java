package com.project.draw.service;

import com.project.draw.dto.request.CreateRoomRequest;
import com.project.draw.dto.response.CreateRoomResponse;
import com.project.draw.entity.Room;
import com.project.draw.entity.User;
import com.project.draw.entity.UserSession;
import com.project.draw.exception.ApplicationException;
import com.project.draw.exception.ErrorCode;
import com.project.draw.repository.RoomRepository;
import com.project.draw.repository.UserRepository;
import com.project.draw.repository.UserSessionRepositoty;
import com.project.draw.service.impl.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RoomServiceImpl implements RoomService {
    RoomRepository roomRepository;
    UserRepository userRepository;
    UserSessionRepositoty userSessionRepositoty;
    String baseUrl = "http://localhost:8080";

    public CreateRoomResponse createRoom(CreateRoomRequest request, UUID userId) {
        User createdBy = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

        String roomCode = generateUniqueRoomCode();

        Room room = Room.builder()
                .roomCode(roomCode)
                .roomName(request.getRoomName())
                .description(request.getDescription())
                .createdBy(createdBy)
                .maxUsers(request.getMaxUsers())
                .isPrivate(request.getIsPrivate())
                .build();

        Room savedRoom = roomRepository.save(room);
        String joinUrl = String.format("%s/join/%s", baseUrl, savedRoom.getRoomCode());

        UserSession userSession = UserSession.builder()
                .room(savedRoom)
                .user(createdBy)
                .joinedAt(LocalDateTime.now())
                .status(UserSession.SessionStatus.ONLINE)
                .build();

        userSessionRepositoty.save(userSession);

        return CreateRoomResponse.builder()
                .id(savedRoom.getId())
                .roomCode(savedRoom.getRoomCode())
                .roomName(savedRoom.getRoomName())
                .desc(savedRoom.getDescription())
                .createdByUsername(createdBy.getUsername())
                .maxUsers(savedRoom.getMaxUsers())
                .joinUrl(joinUrl)
                .onlineUsers(1)
                .build();
    }

    private String generateUniqueRoomCode() {
        while (true) {
            String roomCode = UUID.randomUUID().toString().substring(0, 8);
            boolean exists = roomRepository.findRoomByRoomCode(roomCode).isPresent();
            if (!exists) {
                return roomCode;
            }
        }
    }
}
