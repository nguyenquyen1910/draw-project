package com.project.draw.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignupRequest {
    @NotBlank(message = "Username is required")
    String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email invalid")
    String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password is long than 6 characters")
    String password;
}
