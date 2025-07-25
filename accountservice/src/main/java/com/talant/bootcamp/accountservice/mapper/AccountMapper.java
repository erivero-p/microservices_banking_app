package com.talant.bootcamp.accountservice.mapper;

import com.talant.bootcamp.accountservice.dto.AccountRequest;
import com.talant.bootcamp.accountservice.dto.AccountResponse;
import com.talant.bootcamp.accountservice.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toEntity(AccountRequest accountRequest);
    AccountResponse toDTO(Account account);
}
