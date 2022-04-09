package com.example.security.services;

import com.example.models.ERole;
import com.example.models.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    Optional<Role> findByName(ERole name);

    List<Role> getAllRoles();

    Role saveRole(Role role);

    void deleteRoleById(Long id);

}
