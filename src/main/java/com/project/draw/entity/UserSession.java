package com.project.draw.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_sessions")
public class UserSession extends BaseEntity{
    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID roomId;

    @Column(nullable = false)
    private Boolean isConnected;
    private LocalDateTime joinedAt;
    private LocalDateTime leftAt;
}
