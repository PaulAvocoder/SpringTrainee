package com.galomzik.spring.aop;

import com.galomzik.spring.exception.BadDataException;
import com.galomzik.spring.exception.ConflictException;
import com.galomzik.spring.exception.NotFoundException;
import com.galomzik.spring.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(BadDataException.class)
    public ResponseEntity<String> handlerBadData(BadDataException badDataException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badDataException.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> conflictHandler(ConflictException conflictException) {
        return ResponseEntity.status(409).body(conflictException.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> notFoundHandler(NotFoundException notFoundException) {
        return ResponseEntity.status(404).body(notFoundException.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> unauthorizedHandler(UnauthorizedException unauthorizedException) {
        return ResponseEntity.status(401).body(unauthorizedException.getMessage());
    }

}
