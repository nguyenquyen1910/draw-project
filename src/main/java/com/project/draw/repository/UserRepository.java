package com.project.draw.repository;

import com.project.draw.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(UUID userId);
    Boolean existsUsersByUsername(String username);
    Boolean existsUsersByEmail(String email);
}
