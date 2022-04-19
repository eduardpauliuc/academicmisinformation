package com.example.repositories;

import com.example.models.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGradeRepository extends JpaRepository<Grade, Integer> {
}
