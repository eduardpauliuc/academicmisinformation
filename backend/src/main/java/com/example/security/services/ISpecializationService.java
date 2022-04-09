package com.example.security.services;

import com.example.models.Specialization;

import java.util.List;
import java.util.Optional;

public interface ISpecializationService {

    List<Specialization> getAllSpecializations();

    Specialization saveSpecialization(Specialization specialization);

    void deleteSpecializationById(Long id);

    Optional<Specialization> findSpecializationById(Long id);

}
