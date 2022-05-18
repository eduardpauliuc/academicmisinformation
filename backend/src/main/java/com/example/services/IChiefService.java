package com.example.services;

import com.example.models.Course;
import com.example.models.OptionalProposal;
import com.example.models.Specialization;
import com.example.models.Teacher;
import com.example.payload.responses.CourseDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IChiefService {

    List<CourseDTO> getAllOptionalsBySpecialization(Specialization specialization);

    Teacher saveTeacher(Teacher teacher);

    Optional<Teacher> findTeacherById(Long id);

    void deleteTeacherById(Long id);

    List<Course> getAllCourses(Teacher teacher);

    List<OptionalProposal> getAllOptionalProposals(Teacher teacher);

    Map<Teacher, Double> getAveragesForTeachers(Specialization specialization);

}
