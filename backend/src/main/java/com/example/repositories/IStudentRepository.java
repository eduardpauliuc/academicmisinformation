package com.example.repositories;

import com.example.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Long> {

    Boolean existsByRegistrationNumber(String registrationNumber);

}
