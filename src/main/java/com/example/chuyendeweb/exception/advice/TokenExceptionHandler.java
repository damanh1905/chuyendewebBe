package com.example.chuyendeweb.exception.advice;

import com.example.chuyendeweb.exception.TokenRefreshException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class TokenExceptionHandler {
    @ExceptionHandler(TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<?> handlerTokenRefreshException(TokenRefreshException ex, WebRequest req) {
        ErrorResponse err = new ErrorResponse(HttpStatus.FORBIDDEN.value(),
                new Date(), ex.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
    }
}
