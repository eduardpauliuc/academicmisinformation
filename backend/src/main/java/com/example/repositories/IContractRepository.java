package com.example.repositories;

import com.example.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IContractRepository extends JpaRepository<Contract, Long> {
}
