package com.example.security.security_utils;

import com.example.models.Account;
import com.example.repositories.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AccountDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    IAccountRepository accountRepository;

    @Override
    @Transactional
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No account found with the username: " + username));

        return AccountDetails.build(account);
    }
}