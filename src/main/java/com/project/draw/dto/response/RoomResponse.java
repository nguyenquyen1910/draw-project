package com.project.draw.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomResponse {
    UUID id;
    String roomCode;
    String roomName;
    String desc;
    String createdByUsername;
    Integer maxUsers;
    Integer onlineUsers;
    String joinUrl;
}
