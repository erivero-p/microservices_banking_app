package com.talant.bootcamp.accountservice.repository;


import com.talant.bootcamp.accountservice.model.AccountServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountServiceRepo extends JpaRepository<AccountServiceEntity, Long> {
}
