package com.project.draw.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String email;
    @Column(unique = true ,nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private String avatar;
}
