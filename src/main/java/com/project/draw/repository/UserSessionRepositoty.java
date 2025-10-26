package com.project.draw.repository;

import com.project.draw.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserSessionRepositoty extends JpaRepository<UserSession, UUID> {

}
