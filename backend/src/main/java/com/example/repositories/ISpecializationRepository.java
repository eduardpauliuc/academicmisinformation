package com.example.repositories;

import com.example.models.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISpecializationRepository extends JpaRepository<Specialization, Long> {
}
