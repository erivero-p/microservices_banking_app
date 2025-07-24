package com.talant.bootcamp.accountservice.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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


    public Account() {
    }
    public Account(String name, Double amount) {
        this.name = name;
        this.amount = amount;
    }
    public Account(Integer id, String name, Double amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }
}
