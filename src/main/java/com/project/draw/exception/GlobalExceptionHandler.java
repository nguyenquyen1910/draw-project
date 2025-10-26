package com.project.draw.exception;

import com.project.draw.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Xử lý các exception nghiệp vụ (ApplicationException)
    @ExceptionHandler(value = ApplicationException.class)
    ResponseEntity<ApiResponse> handlingAppException(ApplicationException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getDescription());

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    // Xử lý các exception validate (MethodArgumentNotValidException)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = "Validation failed";

        if(!bindingResult.getFieldErrors().isEmpty()) {
            message = bindingResult.getFieldErrors().get(0).getDefaultMessage();
        } else if(!bindingResult.getGlobalErrors().isEmpty()) {
            message = bindingResult.getGlobalErrors().get(0).getDefaultMessage();
        }

        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.INVALID_VALIDATION.getCode());
        apiResponse.setMessage(message);

        return ResponseEntity
                .status(ErrorCode.INVALID_VALIDATION.getStatusCode())
                .body(apiResponse);
    }
}