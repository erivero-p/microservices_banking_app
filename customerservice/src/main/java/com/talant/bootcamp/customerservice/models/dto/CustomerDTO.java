package com.talant.bootcamp.customerservice.models.dto;

import java.util.UUID;

public record CustomerDTO(
        UUID id,
        String name
        // password
        // accounts
        //etc
) {}