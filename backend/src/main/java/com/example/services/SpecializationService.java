package com.example.services;

import com.example.models.Specialization;
import com.example.payload.responses.SpecializationDTO;
import com.example.repositories.ISpecializationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SpecializationService implements ISpecializationService {

    private final ISpecializationRepository specializationRepository;

    @Override
    public List<Specialization> getAllSpecializations() {
        return specializationRepository.findAll();
    }

    @Override
    public Specialization saveSpecialization(Specialization specialization) {
        return specializationRepository.save(specialization);
    }

    @Override
    public void deleteSpecializationById(Long id) {
        specializationRepository.deleteById(id);
    }

    @Override
    public Optional<Specialization> findSpecializationById(Long id) {
        return specializationRepository.findById(id);
    }

    @Override
    public SpecializationDTO convertToSpecializationDTO(Specialization specialization) {
        return new SpecializationDTO(specialization);
    }
}
