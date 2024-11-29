package com.example.multitenant.config;

import com.example.multitenant.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    protected ApiResponse<?> handleRuntimeException(RuntimeException e) {
        log.error("Got RuntimeException: {}", e.getMessage(), e);


        return ApiResponse.builder()
            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message(e.getMessage())
            .data(null)
            .build();
    }
}
