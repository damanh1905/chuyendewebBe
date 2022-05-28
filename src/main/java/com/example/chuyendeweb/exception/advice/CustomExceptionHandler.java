package com.example.chuyendeweb.exception.advice;

import com.example.chuyendeweb.exception.BadRequestException;
import com.example.chuyendeweb.exception.NotFoundException;
import com.example.chuyendeweb.exception.NullPointerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handlerNotFoundException(NotFoundException ex, WebRequest req) {
        ErrorResponse err = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                new Date(), ex.getMessage(),
                req.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handlerNotFoundException(NullPointerException ex, WebRequest req) {
        ErrorResponse err = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                new Date(), ex.getMessage(),
                req.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handlerBadRequestException(BadRequestException ex, WebRequest req) {
        ErrorResponse err = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                new Date(), ex.getMessage(),
                req.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
    //  Xử lý tất cả các exception chưa được khai báo
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handlerException(Exception ex, WebRequest req){
//        ErrorResponse err = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),new Date(),ex.getMessage(),req.getDescription(false));
//        return new ResponseEntity<>(err,HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
