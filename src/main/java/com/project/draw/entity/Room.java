package com.project.draw.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rooms")
public class Room extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String roomCode;

    @Column(nullable = false)
    private String roomName;
    private String description;
    private String createdBy;
    private Integer maxUsers;
    private Boolean isPrivate;

}
