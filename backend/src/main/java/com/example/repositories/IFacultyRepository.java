package com.example.repositories;

import com.example.models.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFacultyRepository extends JpaRepository<Faculty, Long> {
}
