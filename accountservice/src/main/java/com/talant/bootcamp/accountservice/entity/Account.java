package com.talant.bootcamp.accountservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Customer ID is required")
    @Column(nullable = false)
    private UUID customerId;

    @NotNull(message = "Creation date is required")
    @PastOrPresent(message = "Creation date must be today or in the past")
    @Column(nullable = false)
    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    private AccountState accountState;

    @PositiveOrZero(message = "Account balance cannot be negative")
    private long accountBalance;

    //for test

    public Account(UUID id, UUID customerId, LocalDate creationDate, AccountState accountState, long accountBalance) {
        this.id = id;
        this.customerId = customerId;
        this.creationDate = creationDate;
        this.accountState = accountState;
        this.accountBalance = accountBalance;
    }
}
