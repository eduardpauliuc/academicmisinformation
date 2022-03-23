package com.example.repositories;

import com.example.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAccountRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);

}
