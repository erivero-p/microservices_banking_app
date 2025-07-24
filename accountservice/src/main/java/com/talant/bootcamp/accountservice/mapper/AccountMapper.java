package com.talant.bootcamp.accountservice.mapper;

import com.talant.bootcamp.accountservice.dto.AccountDTO;
import com.talant.bootcamp.accountservice.entity.Account;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDTO toAccountDTO(Account account);
    @Mapping(target = "id", ignore = true)
    Account toAccountEntity(AccountDTO accountDTO);
}
