package com.project.draw.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {
    String email;
    @Column(unique = true ,nullable = false)
    String username;
    @Column(nullable = false)
    String password;
    String avatar;
    @OneToMany(mappedBy = "createdBy")
    List<Room> rooms;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<UserSession> userSessions;

    @OneToMany(mappedBy = "user")
    List<DrawAction> drawActions;
}
