package com.example.services;


import com.example.models.Specialization;
import com.example.models.Student;
import com.example.repositories.IStudentRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

    @Override
    public List<Student> sortStudentsByAverage(Specialization specialization, Integer semester) {
        var students = studentRepository.findAll()
                .stream()
                // filter out students which aren't in our required semester for our specialization
                .filter(student -> student.getSemester(specialization).equals(semester))
                // sort the students by their averages
                .sorted((student1, student2) -> Double.compare(student2.findAverageForSemester(specialization, semester),
                        student1.findAverageForSemester(specialization, semester)))
                .collect(Collectors.toList());
        // finish up by removing those with the average -1, that would be
        // students with no grades in that semester, but with signed contracts or
        // with non-graded courses
        return students.stream().filter(student -> student.findAverageForSemester(specialization, semester) != -1)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> sortStudentsByName(Specialization specialization, Integer semester){
        return studentRepository.findAll()
                .stream()
                // filter out students which aren't in our required semester for our specialization
                .filter(student -> student.getSemester(specialization).equals(semester))
                // sort the students by their full name (firstName + lastName)
                .sorted((student1, student2) -> {
                    var name1 = student1.getAccount().getFirstName() + " " + student1.getAccount().getLastName();
                    var name2 = student2.getAccount().getFirstName() + " " + student2.getAccount().getLastName();
                    return name1.compareTo(name2);
                })
                .collect(Collectors.toList());
    }
}
