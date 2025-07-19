package com.talant.bootcamp.customerservice.exception;

import lombok.Getter;

import java.io.Serial;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {

        super(message);

    }
}
