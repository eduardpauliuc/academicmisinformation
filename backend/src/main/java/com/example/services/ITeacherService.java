package com.example.services;

import com.example.models.Teacher;

import java.util.List;
import java.util.Optional;

public interface ITeacherService {

    List<Teacher> getAllTeachers();

    Teacher saveTeacher(Teacher teacher);

    Optional<Teacher> findTeacherById(Long id);

    void deleteTeacherById(Long id);

}
