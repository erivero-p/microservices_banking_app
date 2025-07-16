package com.talant.bootcamp.customerservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;

@NoArgsConstructor
@AllArgsConstructor
public class CustomerNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private int userId;

}
