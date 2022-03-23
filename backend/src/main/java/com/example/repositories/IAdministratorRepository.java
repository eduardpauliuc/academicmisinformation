package com.example.repositories;

import com.example.models.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdministratorRepository extends JpaRepository<Administrator, Long> {
}
