package com.talant.bootcamp.accountservice.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Account {

    @Id
    private Integer id;
    @NotBlank
    private String name;
    @Min(value = 0)
    private Double amount;
}
