package com.example.services;

import com.example.models.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<Account> findAccountByUsername(String username);

    Account saveAccount(Account account);

    void deleteAccountById(Long id);

    List<Account> getAllAccounts();

    Optional<Account> findAccountById(Long id);

}
