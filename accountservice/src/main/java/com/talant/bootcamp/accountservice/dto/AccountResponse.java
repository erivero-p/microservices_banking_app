package com.talant.bootcamp.accountservice.dto;

import com.talant.bootcamp.accountservice.entity.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponse {
    private Integer id;
    private String name;
    private Double amount;
    public AccountResponse() {
    }

    public AccountResponse(Integer id, String name, Double amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public AccountResponse(Account account) {
        this.id = account.getId();
        this.name = account.getName();
        this.amount = account.getAmount();
    }

    @Override
    public String toString() {
        return "AccountResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }





}
