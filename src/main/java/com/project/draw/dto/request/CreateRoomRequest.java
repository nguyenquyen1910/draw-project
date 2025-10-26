package com.project.draw.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateRoomRequest {
    @NotBlank(message = "Room name is required")
    String roomName;
    String description;
    @Max(value = 100, message = "Maximum users cannot exceed 100")
    Integer maxUsers = 10;
    Boolean isPrivate = false;
}
