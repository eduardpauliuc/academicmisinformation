package com.example.controllers;

import com.example.models.Course;
import com.example.models.Grade;
import com.example.models.OptionalProposal;
import com.example.models.Teacher;
import com.example.payload.requests.GradeRequestDTO;
import com.example.payload.requests.OptionalProposalDTO;
import com.example.payload.responses.CourseDTO;
import com.example.payload.responses.TeacherGradeDTO;
import com.example.services.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@AllArgsConstructor
@RequestMapping("/api/teacher")
public class TeacherController {

    private final ITeacherService teacherService;
    private final IOptionalProposalService optionalProposalService;
    private final ISpecializationService specializationService;
    private final IGradeService gradeService;
    private final IStudentService studentService;
    private final ICourseService courseService;

    @Autowired
    private Logger logger;

    @GetMapping("/courses")
    @PreAuthorize("hasRole('TEACHER') or hasRole('CHIEF')")
    public List<CourseDTO> getTeacherCourses(@RequestHeader("UserId") Long userId) {
        logger.info("Getting the courses of the teacher with the ID " + userId);
        Teacher teacher = this.teacherService.findTeacherById(userId).orElseThrow(
                () -> {
                    logger.warn("Teacher with ID " + userId + " not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Teacher not found.");
                });

        logger.info("Transforming mandatory and optional courses to Course DTOs");
        // convert mandatory and optional Courses to DTOs
        List<CourseDTO> courseDTOS = this.teacherService.getAllCourses(teacher)
                .stream()
                .map(Course::convertToCourseDTO)
                .collect(Collectors.toList());

        logger.info("Transforming proposed courses to Course DTOs");
        // convert pending or rejected OptionalProposals to DTOs
        List<CourseDTO> optionalDTOs = this.teacherService.getAllOptionalProposals(teacher)
                .stream()
                .map(OptionalProposal::convertToCourseDTO)
                .collect(Collectors.toList());
        logger.info("Merging the two lists");
        return Stream.concat(courseDTOS.stream(), optionalDTOs.stream()).collect(Collectors.toList());
    }

    @PostMapping(value = "/optional", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('TEACHER') or hasRole('CHIEF')")
    public void createOptionalProposal(@RequestBody OptionalProposalDTO optionalProposalDTO) {
        logger.info("Teacher with ID " + optionalProposalDTO.getTeacherId() + " creating new optional proposal for " +
                "the specialization with ID " + optionalProposalDTO.getSpecializationId());
        this.teacherService.findTeacherById(optionalProposalDTO.getTeacherId()).orElseThrow(
                () -> {
                    logger.warn("Teacher with ID " + optionalProposalDTO.getTeacherId() + " not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Teacher not found.");
                }
        );

        this.specializationService.findSpecializationById(optionalProposalDTO.getSpecializationId()).orElseThrow(
                () -> {
                    logger.warn("Specialization with ID " + optionalProposalDTO.getSpecializationId() + " not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found.");
                }
        );

        OptionalProposal optionalProposal = this.optionalProposalService.convertToOptionalProposal(optionalProposalDTO);
        this.optionalProposalService.saveOptionalProposal(optionalProposal);
        logger.info("Optional proposal saved successfully!");
    }

    @PostMapping(value = "/grade", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('TEACHER') or hasRole('CHIEF')")
    public void createGrade(@RequestBody GradeRequestDTO gradeRequestDTO) {
        logger.info("Adding grade for the student with the ID " + gradeRequestDTO.getStudentId() + " at the course with" +
                " the ID " + gradeRequestDTO.getCourseId());
        this.studentService.findStudentById(gradeRequestDTO.getStudentId()).orElseThrow(
                () -> {
                    logger.warn("Student with ID " + gradeRequestDTO.getStudentId() + " not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.");
                });

        this.courseService.findCourseById(gradeRequestDTO.getCourseId()).orElseThrow(
                () -> {
                    logger.warn("Course with ID " + gradeRequestDTO.getCourseId() + " not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Course not found.");
                });

        Grade grade = this.gradeService.convertToGrade(gradeRequestDTO);
        this.gradeService.saveGrade(grade);
        logger.info("Grade saved successfully!");
    }

    @GetMapping(value = "/students/{courseId}", produces = "application/json")
    @PreAuthorize("hasRole('TEACHER') or hasRole('CHIEF')")
    public List<TeacherGradeDTO> getStudentsForCourse(@PathVariable Long courseId){
        logger.info("Getting students for course with id " + courseId);
        Course course = this.courseService.findCourseById(courseId).orElseThrow(
                () -> {
                    logger.warn("Course with id " + courseId + " not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Course not found.");
                }
        );

        List<TeacherGradeDTO> grades =
                course.getGrades().stream().map(TeacherGradeDTO::new).collect(Collectors.toList());
        logger.info("Students returned successfully!");
        return grades;
    }
}
