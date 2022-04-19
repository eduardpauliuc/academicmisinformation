package com.example.services;

import com.example.models.Course;
import com.example.models.OptionalProposal;
import com.example.models.Teacher;
import com.example.repositories.ITeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TeacherService implements ITeacherService {

    private final ITeacherRepository teacherRepository;

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public Optional<Teacher> findTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    @Override
    public void deleteTeacherById(Long id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public List<Course> getAllCourses(Teacher teacher) {
        return teacher.getCourses();
    }

    @Override
    public List<OptionalProposal> getAllOptionalProposals(Teacher teacher) {
        return teacher.getOptionalProposals();
    }
}
