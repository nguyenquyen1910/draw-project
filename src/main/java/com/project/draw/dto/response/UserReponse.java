package com.project.draw.dto.response;

import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserReponse {
    UUID id;
    String username;
    String email;
    String avatar;
}
