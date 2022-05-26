package com.example.repositories;

import com.example.models.Course;
import com.example.models.Grade;
import com.example.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IGradeRepository extends JpaRepository<Grade, Integer> {
    Optional<Grade> findGradeByCourseAndStudent(Course course, Student student);
}
