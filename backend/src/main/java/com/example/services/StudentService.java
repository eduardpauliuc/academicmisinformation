package com.example.services;


import com.example.models.Student;
import com.example.repositories.IStudentRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService implements IStudentService {

    private final IStudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Optional<Student> findStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Boolean existsByRegistrationNumber(String registrationNumber) {
        return studentRepository.existsByRegistrationNumber(registrationNumber);
    }

    @Override
    public String generateUniqueRegistrationNumber() {
        String registrationNumber = RandomStringUtils.randomAlphanumeric(10);
        while (this.existsByRegistrationNumber(registrationNumber)) {
            registrationNumber = RandomStringUtils.randomAlphanumeric(10).toUpperCase(Locale.ROOT);
        }
        return registrationNumber;
    }
}
