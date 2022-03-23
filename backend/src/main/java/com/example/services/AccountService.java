package com.example.services;

import com.example.models.Account;
import com.example.repositories.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements org.springframework.security.core.userdetails.UserDetailsService {

    private IAccountRepository accountsRepository;

    public AccountService(IAccountRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.accountsRepository.findByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException(username);
        }

        return account;
    }

    public List<Account> getAccounts() {
        return this.accountsRepository.findAll();
    }

    public Account getAccountById(Long id) {
        Optional<Account> accountWrapper = this.accountsRepository.findById(id);

        if (accountWrapper.isEmpty()) {
            throw new EntityNotFoundException("Account with id " + id + " not found!");
        }

        return accountWrapper.get();
    }

}
