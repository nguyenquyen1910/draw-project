package com.project.draw.exception;

import com.project.draw.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * GlobalExceptionHandler xử lý tất cả các exception.
 * - Controller không cần try-catch
 * - Trả về ApiResponse khi có lỗi.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    // Xử lý các exceptiion nghiệp vụ (ApplicationException)
    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<ApiResponse<?>> applicationException(final ApplicationException e) {

        ApiResponse<?> apiResponse = new ApiResponse<>();
        ErrorCode errorCode = e.getErrorCode();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(apiResponse);

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

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(ErrorCode.INVALID_VALIDATION.getCode())
                .message(message)
                .build();
        return ResponseEntity
                .status(ErrorCode.INVALID_VALIDATION.getHttpStatusCode())
                .body(apiResponse);
    }
}
