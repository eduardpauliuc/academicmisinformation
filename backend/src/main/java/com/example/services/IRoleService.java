package com.example.services;

import com.example.models.ERole;
import com.example.models.Role;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(ERole name);
}
