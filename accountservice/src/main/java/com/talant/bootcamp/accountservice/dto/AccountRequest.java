package com.talant.bootcamp.accountservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class AccountRequest{
    private Integer id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Min(value = 0, message = "Amount must be at least 0")
    private Double amount;

    public AccountRequest() {
    }
    public AccountRequest(Integer id, String name, Double amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public AccountRequest(String testAccount, Double v) {
        this.name = testAccount;
        this.amount = v;
    }

    @Override
    public String toString() {
        return "AccountRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }


}
