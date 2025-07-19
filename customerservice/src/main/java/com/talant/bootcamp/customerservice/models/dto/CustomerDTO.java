package com.talant.bootcamp.customerservice.models.dto;

import com.talant.bootcamp.customerservice.validation.annotations.ValidBirthday;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record CustomerDTO(

        UUID id,
        @NotBlank
        String name,
        @NotNull
        @ValidBirthday
        LocalDate birthday,
        @Email
        @NotBlank
        String email

        // password
        // accounts
        //etc
) {}