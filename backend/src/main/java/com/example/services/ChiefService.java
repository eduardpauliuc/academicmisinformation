package com.example.services;

import com.example.models.*;
import com.example.payload.responses.CourseDTO;
import com.example.repositories.ICourseRepository;
import com.example.repositories.IOptionalProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ChiefService implements IChiefService {

    @Autowired
    private ITeacherService teacherService;

    @Autowired
    private ICourseRepository courseRepository;

    @Autowired
    private IOptionalProposalRepository optionalProposalRepository;


    @Override
    public List<CourseDTO> getAllOptionalsBySpecialization(Specialization specialization) {
        List<CourseDTO> acceptedOptionals = courseRepository.findAll()
                .stream()
                .filter(Course::getIsOptional)
                .filter(course -> course.getSpecialization().equals(specialization))
                .map(CourseDTO::new)
                .collect(Collectors.toList());

        List<CourseDTO> pendingOptionals = optionalProposalRepository.findAll()
                .stream()
                .filter(optionalProposal -> optionalProposal.getSpecialization().equals(specialization))
                .map(CourseDTO::new)
                .collect(Collectors.toList());

        return Stream.concat(acceptedOptionals.stream(), pendingOptionals.stream()).collect(Collectors.toList());
    }

    @Override
    public Teacher saveTeacher(Teacher teacher) {
        return teacherService.saveTeacher(teacher);
    }

    @Override
    public Optional<Teacher> findTeacherById(Long id) {
        return teacherService.findTeacherById(id);
    }

    @Override
    public void deleteTeacherById(Long id) {
        teacherService.deleteTeacherById(id);
    }

    @Override
    public List<Course> getAllCourses(Teacher teacher) {
        return teacherService.getAllCourses(teacher);
    }

    @Override
    public List<OptionalProposal> getAllOptionalProposals(Teacher teacher) {
        return teacherService.getAllOptionalProposals(teacher);
    }

    @Override
    public Map<Teacher, Double> getAveragesForTeachers(Specialization specialization) {
        List<Teacher> teachers = teacherService.getAllTeachers();
        Map<Teacher, Double> averages = new ConcurrentHashMap<>();
        teachers.forEach(
                teacher -> {

                    AtomicInteger gradeSum = new AtomicInteger(0);
                    AtomicInteger gradeCount = new AtomicInteger(0);
                    teacher.getCourses().stream()
                            .filter(course -> course.getSpecialization().equals(specialization)).forEach(
                            course -> {
                                List<Grade> grades = course.getGrades();
                                gradeSum.addAndGet(
                                        grades.stream().map(Grade::getGrade)
                                                .reduce(0, Integer::sum)
                                );
                                gradeCount.addAndGet(
                                        grades.size());
                            });

                    if (gradeCount.get() != 0){
                        Double average = (double) gradeSum.get() / (double) gradeCount.get();
                        averages.put(teacher, average);
                    }
                });
    return averages;
    }
}
