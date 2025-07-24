package com.talant.bootcamp.accountservice.repository;

import com.talant.bootcamp.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
