package com.talant.bootcamp.customerservice.controller;

import com.talant.bootcamp.customerservice.exception.CustomerNotFoundException;
import com.talant.bootcamp.customerservice.models.entity.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorController {

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleCustomerNotFoundException(CustomerNotFoundException exception){
        return ApiError.builder()
                .code(404)
                .message("Customer not found.")
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleException(Exception exception){
        return ApiError.builder()
                .code(500)
                .message("Uncaught Error: " + exception.getMessage())
                .build();
    }
}
