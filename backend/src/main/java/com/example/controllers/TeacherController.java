package com.example.controllers;

import com.example.models.Course;
import com.example.models.OptionalProposal;
import com.example.models.Teacher;
import com.example.payload.requests.OptionalProposalDTO;
import com.example.payload.responses.CourseDTO;
import com.example.services.IOptionalProposalService;
import com.example.services.ISpecializationService;
import com.example.services.ITeacherService;
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

    @GetMapping("/courses")
    @PreAuthorize("hasRole('TEACHER') or hasRole('CHIEF')")
    public List<CourseDTO> getTeacherCourses(@RequestHeader("UserId") Long userId) {
        Teacher teacher = this.teacherService
                .findTeacherById(userId)
                .orElseThrow(
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
        if (this.teacherService.findTeacherById(optionalProposalDTO.getTeacherId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Teacher not found.");
        }

        if (this.specializationService.findSpecializationById(optionalProposalDTO.getSpecializationId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Specialization not found.");
        }

        OptionalProposal optionalProposal = this.optionalProposalService.convertToOptionalProposal(optionalProposalDTO);
        this.optionalProposalService.saveOptionalProposal(optionalProposal);
    }
}
