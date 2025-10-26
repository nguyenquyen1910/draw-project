package com.project.draw.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "draw_actions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DrawAction extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    String actionType;

    Double startX;
    Double startY;
    Double endX;
    Double endY;
    Double lineWidth;

    String color;
    @Column(nullable = false)
    Long sequenceNumber;

}
