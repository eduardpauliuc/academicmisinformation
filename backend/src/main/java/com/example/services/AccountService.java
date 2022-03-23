package com.example.services;

import com.example.models.Account;
import com.example.repositories.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private IAccountRepository accountsRepository;

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
}
