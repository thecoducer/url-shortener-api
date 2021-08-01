package com.thecoducer.shortenurl.exception;

import org.springframework.aop.AopInvocationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.TreeMap;

@ControllerAdvice
public class GlobalExceptionHandler {
    private Map<String, String> response = new TreeMap<String, String>();

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleMissingParams(MissingServletRequestParameterException ex) {
        response.put("message", "Required parameters are missing.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(AopInvocationException.class)
    public ResponseEntity<Map<String, String>> handleUrlNotPresent(AopInvocationException ex) {
        response.put("message", "The entered URL doesn't exist.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
