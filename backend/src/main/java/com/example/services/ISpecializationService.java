package com.example.services;

import com.example.models.Specialization;
import com.example.payload.responses.SpecializationDTO;

import java.util.List;
import java.util.Optional;

public interface ISpecializationService {

    List<Specialization> getAllSpecializations();

    Specialization saveSpecialization(Specialization specialization);

    void deleteSpecializationById(Long id);

    Optional<Specialization> findSpecializationById(Long id);

    SpecializationDTO convertToSpecializationDTO(Specialization specialization);

}
