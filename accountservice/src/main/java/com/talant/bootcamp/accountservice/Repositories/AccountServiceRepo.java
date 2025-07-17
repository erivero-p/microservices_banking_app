package com.talant.bootcamp.accountservice.Repositories;


import com.talant.bootcamp.accountservice.Service.AccountServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountServiceRepo extends JpaRepository<AccountServiceEntity, Long> {
}
