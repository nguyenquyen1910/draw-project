package com.project.draw.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "draw_actions")
public class DrawAction extends BaseEntity {
    @Column(nullable = false)
    private UUID roomId;
    @Column(nullable = false)
    private UUID userId;

    private String actionType;

    private Double startX;
    private Double startY;
    private Double endX;
    private Double endY;
    private Double lineWidth;

    private String color;
    @Column(nullable = false)
    private Long sequenceNumber;

}
