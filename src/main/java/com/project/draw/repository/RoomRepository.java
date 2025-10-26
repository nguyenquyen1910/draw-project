package com.project.draw.repository;

import com.project.draw.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    Optional<Room> findRoomByRoomCode(String roomCode);
    Optional<Room> findRoomByRoomName(String roomName);
}
