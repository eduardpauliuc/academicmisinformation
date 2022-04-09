package com.example.security.services;

import com.example.models.Course;

import java.util.List;
import java.util.Optional;

public interface ICourseService {

    List<Course> getAllCourses();

    Optional<Course> findCourseById(Long id);

    Course saveCourse(Course course);

    void deleteCourseById(Long id);

}
