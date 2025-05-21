package com.virginholidays.backend.test.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Object> handleHttpClientError(HttpClientErrorException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", ex.getStatusCode().value());
        errorDetails.put("error", ex.getStatusText());
        errorDetails.put("message", ex.getMessage());

        return new ResponseEntity<>(errorDetails, ex.getStatusCode());
    }

    // optional: catch other exceptions too if you like
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", 500);
        errorDetails.put("error", "Internal Server Error");
        errorDetails.put("message", ex.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}