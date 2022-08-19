package com.example.client.controllers;

import com.example.client.exception.DiscriminantException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(DiscriminantException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public DiscriminantException handleDiscriminantException(DiscriminantException de){
        return de;
    }
}
