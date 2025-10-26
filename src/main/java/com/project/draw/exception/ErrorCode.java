package com.project.draw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    ROOM_NOT_FOUND(1001, "Room not found", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(1002, "User not found", HttpStatus.NOT_FOUND),
    INVALID_VALIDATION(1003,"Invalid Validation", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1004, "Unauthenticated access", HttpStatus.UNAUTHORIZED),
    INVALID_CREDENTIALS(1005, "Invalid credentials", HttpStatus.UNAUTHORIZED),
    USER_ALREADY_EXISTS(1006, "User already exists", HttpStatus.CONFLICT),

    ;

    private final int code;
    private final String description;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String description, HttpStatusCode statusCode) {
        this.code = code;
        this.description = description;
        this.statusCode = statusCode;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }
}