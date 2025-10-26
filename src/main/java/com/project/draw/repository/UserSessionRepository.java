package com.project.draw.repository;

import com.project.draw.entity.Room;
import com.project.draw.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, UUID> {
    @Query("""
        SELECT us.room.id, COUNT(us) 
        FROM UserSession us 
        WHERE us.room.id IN :roomIds 
        AND us.status = 'ONLINE' 
        GROUP BY us.room.id
    """)
    List<Object[]> countOnlineUsersByRoomIds(@Param("roomIds") List<UUID> roomIds);
    
    int countByRoomAndStatus(Room room, UserSession.SessionStatus status);
}
