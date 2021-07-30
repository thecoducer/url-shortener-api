package com.thecoducer.shortenurl.exception;

import com.thecoducer.shortenurl.dto.ErrorResponse;
import org.springframework.aop.AopInvocationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParams(MissingServletRequestParameterException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage("Required parameters are missing.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(AopInvocationException.class)
    public ResponseEntity<ErrorResponse> handleUrlNotPresent(AopInvocationException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage("The entered URL doesn't exist.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
