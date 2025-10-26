-- Create database if not exists
CREATE DATABASE IF NOT EXISTS draw_project;

-- Use the database
USE draw_project;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id CHAR(36) PRIMARY KEY,
    email VARCHAR(255),
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    avatar VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create rooms table
CREATE TABLE IF NOT EXISTS rooms (
    id CHAR(36) PRIMARY KEY,
    room_code VARCHAR(255) UNIQUE NOT NULL,
    room_name VARCHAR(255) NOT NULL,
    description TEXT,
    created_by VARCHAR(255),
    max_users INT,
    is_private BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create user_sessions table
CREATE TABLE IF NOT EXISTS user_sessions (
    id CHAR(36) PRIMARY KEY,
    user_id CHAR(36) NOT NULL,
    room_id CHAR(36) NOT NULL,
    is_connected BOOLEAN NOT NULL DEFAULT FALSE,
    joined_at TIMESTAMP,
    left_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE
);

-- Create draw_actions table
CREATE TABLE IF NOT EXISTS draw_actions (
    id CHAR(36) PRIMARY KEY,
    room_id CHAR(36) NOT NULL,
    user_id CHAR(36) NOT NULL,
    action_type VARCHAR(50),
    start_x DOUBLE,
    start_y DOUBLE,
    end_x DOUBLE,
    end_y DOUBLE,
    line_width DOUBLE,
    color VARCHAR(20),
    sequence_number BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_user_sessions_user_id ON user_sessions(user_id);
CREATE INDEX idx_user_sessions_room_id ON user_sessions(room_id);
CREATE INDEX idx_draw_actions_room_id ON draw_actions(room_id);
CREATE INDEX idx_draw_actions_user_id ON draw_actions(user_id);
CREATE INDEX idx_draw_actions_sequence ON draw_actions(room_id, sequence_number);

-- Insert sample data
INSERT INTO users (id, email, username, password, avatar) VALUES
('550e8400-e29b-41d4-a716-446655440000', 'admin@draw.com', 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'https://via.placeholder.com/150'),
('550e8400-e29b-41d4-a716-446655440001', 'user1@draw.com', 'user1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'https://via.placeholder.com/150'),
('550e8400-e29b-41d4-a716-446655440002', 'user2@draw.com', 'user2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'https://via.placeholder.com/150');

INSERT INTO rooms (id, room_code, room_name, description, created_by, max_users, is_private) VALUES
('650e8400-e29b-41d4-a716-446655440000', 'ROOM001', 'General Drawing Room', 'A room for general drawing activities', 'admin', 10, FALSE),
('650e8400-e29b-41d4-a716-446655440001', 'ROOM002', 'Private Art Session', 'Private room for art sessions', 'user1', 5, TRUE);
