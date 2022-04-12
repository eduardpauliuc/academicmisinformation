package com.example.services;

import com.example.models.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentService {

    List<Student> getAllStudents();

    Student saveStudent(Student student);

    void deleteStudentById(Long id);

    Optional<Student> findStudentById(Long id);

    Boolean existsByRegistrationNumber(String registrationNumber);

    String generateUniqueRegistrationNumber();
}
