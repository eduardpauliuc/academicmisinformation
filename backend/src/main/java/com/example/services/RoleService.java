package com.example.services;


import com.example.models.ERole;
import com.example.models.Role;
import com.example.repositories.IRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService implements IRoleService {

    private final IRoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(ERole name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteRoleById(Long id) {
        roleRepository.deleteById(id);
    }
}
