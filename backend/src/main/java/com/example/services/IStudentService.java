package com.example.services;

import com.example.models.Specialization;
import com.example.models.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentService {

    List<Student> getAllStudents();

    Student saveStudent(Student student);

    void deleteStudentById(Long id);

    Optional<Student> findStudentById(Long id);

    // sorts the list of students from a given specialization and who are studying
    // in a given semester by their average
    // we could return a list of StudentGradeDTO so we do not recompute the
    // average wherever we use this function
    List<Student> sortStudentsByAverage(Specialization specialization, Integer semester);

    // sorts the list of students from a given specialization and who are studying
    // in a given semester by their name
    List<Student> sortStudentsByName(Specialization specialization, Integer semester);
}
