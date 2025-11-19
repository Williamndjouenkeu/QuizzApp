package com.will.quiz.exception.handler;

import com.will.quiz.exception.BadRequestException;
import com.will.quiz.exception.NotFoundException;
import com.will.quiz.exception.RefreshTokenException;
import com.will.quiz.model.response.ErrorApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorApiResponse> handleException(BadRequestException e){
        return handleException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorApiResponse> handleException(NotFoundException e) {
        return handleException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = RefreshTokenException.class)
    public ResponseEntity<ErrorApiResponse> handleException(RefreshTokenException e) {
        return handleException(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorApiResponse> handleException(Exception e) {
        return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorApiResponse> handleException(Exception e, HttpStatus status) {
        log.error(e.getMessage(), e);
        var body = new ErrorApiResponse(status.value(), e.getMessage());
        return new ResponseEntity<>(body, status);
    }
}
