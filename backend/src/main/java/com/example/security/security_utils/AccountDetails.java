package com.example.security.security_utils;

import com.example.models.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
public class AccountDetails implements org.springframework.security.core.userdetails.UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private String firstName;
    private String lastName;
    private Date birthDate;

    public AccountDetails(Long id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities, String firstName, String lastName, Date birthDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public static AccountDetails build(Account account) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(account.getRole().getName().name()));

        return new AccountDetails(
                account.getId(),
                account.getUsername(),
                account.getEmail(),
                account.getPasswordDigest(),
                authorities,
                account.getFirstName(),
                account.getLastName(),
                account.getBirthDate()
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null || this.getClass() != other.getClass()) {
            return false;
        }

        AccountDetails account = (AccountDetails) other;
        return Objects.equals(this.id, account.id);
    }
}
