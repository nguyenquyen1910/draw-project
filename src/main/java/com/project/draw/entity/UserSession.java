package com.project.draw.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_sessions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSession extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    LocalDateTime joinedAt;
    LocalDateTime leftAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    SessionStatus status;

    public enum SessionStatus {
        ONLINE,
        OFFLINE
    }
}
