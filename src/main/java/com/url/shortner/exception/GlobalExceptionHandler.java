package com.url.shortner.exception;

import com.url.shortner.dto.ApiResponse;

import tools.jackson.databind.exc.InvalidFormatException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

     
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusinessException(BusinessException ex) {

        log.warn("Business exception occurred: {}", ex.getMessage());

        ApiResponse<Object> response = new ApiResponse<>(
                false,
                ex.getErrorCode(),
                ex.getMessage(),
                null,
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        ApiResponse<Object> response = new ApiResponse<>(
                false,
                ErrorCodes.VALIDATION_ERROR,
                "Validation failed",
                null,
                fieldErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex) {

        ApiResponse<Object> response = new ApiResponse<>(
                false,
                ErrorCodes.VALIDATION_ERROR,
                "Invalid value provided for request field",
                null,
                null
        );

        return ResponseEntity.badRequest().body(response);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {

        log.error("Unhandled exception occurred", ex);

        ApiResponse<Object> response = new ApiResponse<>(
                false,
                ErrorCodes.INTERNAL_ERROR,
                "Internal Server Error",
                null,
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
