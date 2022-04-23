package com.example.controllers;

import com.example.models.Course;
import com.example.models.Grade;
import com.example.models.OptionalProposal;
import com.example.models.Teacher;
import com.example.payload.requests.GradeRequestDTO;
import com.example.payload.requests.OptionalProposalDTO;
import com.example.payload.responses.CourseDTO;
import com.example.services.*;
import lombok.AllArgsConstructor;
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

    @GetMapping("/courses")
    @PreAuthorize("hasRole('TEACHER') or hasRole('CHIEF')")
    public List<CourseDTO> getTeacherCourses(@RequestHeader("UserId") Long userId) {
        Teacher teacher = this.teacherService.findTeacherById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Teacher not found.")
        );

        // convert mandatory and optional Courses to DTOs
        List<CourseDTO> courseDTOS = this.teacherService.getAllCourses(teacher)
                .stream()
                .map(Course::convertToCourseDTO)
                .collect(Collectors.toList());

        // convert pending or rejected OptionalProposals to DTOs
        List<CourseDTO> optionalDTOs = this.teacherService.getAllOptionalProposals(teacher)
                .stream()
                .map(OptionalProposal::convertToCourseDTO)
                .collect(Collectors.toList());

        return Stream.concat(courseDTOS.stream(), optionalDTOs.stream()).collect(Collectors.toList());
    }

    @PostMapping(value = "/optional", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('TEACHER') or hasRole('CHIEF')")
    public void createOptionalProposal(@RequestBody OptionalProposalDTO optionalProposalDTO) {
        this.teacherService.findTeacherById(optionalProposalDTO.getTeacherId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Teacher not found.")
        );

        this.specializationService.findSpecializationById(optionalProposalDTO.getSpecializationId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found.")
        );

        OptionalProposal optionalProposal = this.optionalProposalService.convertToOptionalProposal(optionalProposalDTO);
        this.optionalProposalService.saveOptionalProposal(optionalProposal);
    }

    @PostMapping(value = "/grade", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('TEACHER') or hasRole('CHIEF')")
    public void createGrade(@RequestBody GradeRequestDTO gradeRequestDTO) {
        this.studentService.findStudentById(gradeRequestDTO.getStudentId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Student not found.")
        );

        this.courseService.findCourseById(gradeRequestDTO.getCourseId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Course not found.")
        );

        Grade grade = this.gradeService.convertToGrade(gradeRequestDTO);
        this.gradeService.saveGrade(grade);
    }

}
