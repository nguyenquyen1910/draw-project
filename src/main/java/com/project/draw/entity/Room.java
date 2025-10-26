package com.project.draw.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rooms")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Room extends BaseEntity {
    @Column(unique = true, nullable = false)
    String roomCode;

    @Column(nullable = false)
    String roomName;
    String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    User createdBy;

    Integer maxUsers;
    Boolean isPrivate;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    List<UserSession> userSessions;

    @OneToMany(mappedBy = "room")
    List<DrawAction> drawActions;
}
