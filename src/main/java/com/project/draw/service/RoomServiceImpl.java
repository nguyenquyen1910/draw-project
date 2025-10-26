package com.project.draw.service;

import com.project.draw.dto.request.CreateRoomRequest;
import com.project.draw.dto.response.RoomResponse;
import com.project.draw.entity.Room;
import com.project.draw.entity.User;
import com.project.draw.entity.UserSession;
import com.project.draw.exception.ApplicationException;
import com.project.draw.exception.ErrorCode;
import com.project.draw.repository.RoomRepository;
import com.project.draw.repository.UserRepository;
import com.project.draw.repository.UserSessionRepository;
import com.project.draw.service.impl.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RoomServiceImpl implements RoomService {
    RoomRepository roomRepository;
    UserRepository userRepository;
    UserSessionRepository userSessionRepository;
    @NonFinal
    @Value("${app.base-url}")   
    String baseUrl;

    public RoomResponse createRoom(CreateRoomRequest request, UUID userId) {
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

        userSessionRepository.save(userSession);

        return RoomResponse.builder()
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

    public Page<RoomResponse> getAllRooms(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomRepository.findAll(pageable);
        Map<UUID, Integer> onlineCountMap = getOnlineCountMap(roomPage.getContent());
        return roomPage.map(room -> {
            String joinUrl = String.format("%s/join/%s", baseUrl, room.getRoomCode());
            int onlineUsers = onlineCountMap.getOrDefault(room.getId(), 0);
            return RoomResponse.builder()
                    .id(room.getId())
                    .roomCode(room.getRoomCode())
                    .roomName(room.getRoomName())
                    .desc(room.getDescription())
                    .createdByUsername(room.getCreatedBy().getUsername())
                    .maxUsers(room.getMaxUsers())
                    .onlineUsers(onlineUsers)
                    .joinUrl(joinUrl)
                    .build();
        });
    }

    public RoomResponse getRoomById(UUID roomId) {
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new ApplicationException(ErrorCode.ROOM_NOT_FOUND));
        int onlineUsers = userSessionRepository
            .countByRoomAndStatus(room, UserSession.SessionStatus.ONLINE);
        String joinUrl = String.format("%s/join/%s", baseUrl, room.getRoomCode());
        return RoomResponse.builder()
            .id(room.getId())
            .roomCode(room.getRoomCode())
            .roomName(room.getRoomName())
            .desc(room.getDescription())
            .createdByUsername(room.getCreatedBy().getUsername())
            .maxUsers(room.getMaxUsers())
            .onlineUsers(onlineUsers)
            .joinUrl(joinUrl)
            .build();
    }


    public Boolean deleteRoomById(UUID roomId) {
        if(!roomRepository.existsById(roomId)) {
            throw new ApplicationException(ErrorCode.ROOM_NOT_FOUND);
        }
        roomRepository.deleteById(roomId);
        return true;
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

    private Map<UUID, Integer> getOnlineCountMap(List<Room> rooms) {
        if (rooms.isEmpty()) {
            return Collections.emptyMap();
        }
        
        List<UUID> roomIds = rooms.stream()
                .map(Room::getId)
                .toList();
        
        List<Object[]> counts = userSessionRepository
                .countOnlineUsersByRoomIds(roomIds);
        
        return counts.stream()
                .collect(Collectors.toMap(
                    arr -> (UUID) arr[0],      
                    arr -> ((Long) arr[1]).intValue(), 
                    (a, b) -> a
                ));
    }
}
