package com.talant.bootcamp.accountservice.mapper;

import com.talant.bootcamp.accountservice.dto.AccountDTO;
import com.talant.bootcamp.accountservice.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toEntity(AccountDTO accountDTO);
    AccountDTO toDTO(Account account);
}
