package com.example.services;

import com.example.models.Contract;
import com.example.repositories.IContractRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ContractService implements IContractService{
    private IContractRepository contractRepository;

    @Override
    public Contract saveContract(Contract contract) {
        return contractRepository.save(contract);
    }
}
