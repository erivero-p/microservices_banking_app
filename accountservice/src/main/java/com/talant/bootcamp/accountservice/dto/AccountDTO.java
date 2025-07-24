package com.talant.bootcamp.accountservice.dto;

import com.talant.bootcamp.accountservice.entity.AccountState;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

public record AccountDTO (
        UUID id,
        UUID customerId,
        LocalDate creationDate,
        AccountState accountState,
        long accountBalance
){}
