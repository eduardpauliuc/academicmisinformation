package com.example.services;

import com.example.models.Account;

import java.util.Optional;

public interface IAccountService {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<Account> findByUsername(String username);

    Account save(Account account);
}
