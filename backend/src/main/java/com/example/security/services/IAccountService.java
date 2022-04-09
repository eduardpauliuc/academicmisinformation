package com.example.security.services;

import com.example.models.Account;

import java.util.Optional;

public interface IAccountService {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<Account> findAccountByUsername(String username);

    Account saveAccount(Account account);

    void deleteAccountById(Long id);


}
