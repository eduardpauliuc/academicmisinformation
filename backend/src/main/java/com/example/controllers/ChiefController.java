package com.example.controllers;

import com.example.models.Course;
import com.example.models.OptionalProposal;
import com.example.models.Specialization;
import com.example.models.Teacher;
import com.example.payload.requests.OptionalReviewDTO;
import com.example.payload.responses.CourseDTO;
import com.example.payload.responses.RankingDTO;
import com.example.services.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/chief/{id}")
public class ChiefController {

    private final IChiefService chiefService;
    private final IOptionalProposalService optionalProposalService;
    private final ICourseService courseService;
    private final IStatusService statusService;
    private final ISpecializationService specializationService;

    @GetMapping("/optionals")
    @PreAuthorize("hasRole('CHIEF')")
    public List<CourseDTO> getAllOptionals(@PathVariable("id") Long id) {
           Specialization specialization = getSpecializationOfChief(id);
           return chiefService.getAllOptionalsBySpecialization(specialization);
    }

    @PostMapping("/optionals/{optionalId}")
    @PreAuthorize("hasRole('CHIEF')")
    public void reviewOptional(
            @PathVariable("id") Long id,
            @PathVariable("optionalId") Long optionalId,
            @RequestBody OptionalReviewDTO optionalReviewDTO
    ) {
        OptionalProposal optionalProposal = optionalProposalService.findOptionalProposalById(optionalId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Optional proposal not found.")
        );

        if (optionalReviewDTO.getStatus()) {
            Course acceptedOptional = new Course(optionalProposal);
            courseService.saveCourse(acceptedOptional);
            optionalProposalService.deleteOptionalProposal(optionalId);
        } else {
            optionalProposal.setComments(optionalReviewDTO.getReviewMessage());
            optionalProposal.setStatus(statusService.getRejectedStatus());
            optionalProposalService.saveOptionalProposal(optionalProposal);
        }
    }

    @GetMapping("/teachers/disciplines/{teacherId}")
    @PreAuthorize("hasRole('CHIEF')")
    public List<CourseDTO> getDisciplinesForTeacher(@PathVariable("id") Long id,
                                                    @PathVariable("teacherId") Long teacherId){
        Teacher teacher = chiefService.findTeacherById(teacherId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Teacher not found")
        );

        Specialization specialization = getSpecializationOfChief(id);

        if (!specialization.getChiefOfDepartment().getId().equals(id))
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid chief");
        List<CourseDTO> acceptedCourses = teacher.getCourses().stream()
                .filter(course -> course.getSpecialization().equals(specialization))
                .map(CourseDTO::new)
                .collect(Collectors.toList());

        List<CourseDTO> notYetAcceptedCourses = teacher.getOptionalProposals().stream()
                .map(CourseDTO::new)
                .collect(Collectors.toList());

        acceptedCourses.addAll(notYetAcceptedCourses);
        return acceptedCourses;
    }

    @GetMapping("/teachers/rankings")
    @PreAuthorize("hasRole('CHIEF')")
    public List<RankingDTO> getTeacherRankings(@PathVariable("id") Long id){
        // we should change this to courses I suppose
        Specialization specialization = getSpecializationOfChief(id);
        Map<Teacher, Double> averages = chiefService.getAveragesForTeachers(specialization);
        List<Teacher> orderedTeachers = averages.keySet().stream()
                .sorted(Comparator.comparingDouble(t -> -averages.get(t)))
                .collect(Collectors.toList());

        List<RankingDTO> dtos = new LinkedList<>();
        for (int i = 0; i < orderedTeachers.size(); i++){
            String fullName = orderedTeachers.get(i).getAccount().getFirstName() + " " + orderedTeachers.get(i).getAccount().getLastName();
            dtos.add(new RankingDTO(fullName, i));
        }
        return dtos;
    }

    private Specialization getSpecializationOfChief(Long id){
        List<Specialization> specializations =
                specializationService.getAllSpecializations().stream()
                        .filter(specialization -> specialization.getChiefOfDepartment() != null &&
                                id.equals(specialization.getChiefOfDepartment().getId()))
                        .collect(Collectors.toList());
        if (specializations.isEmpty())
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There is no specialization that the teacher is the chief of");
        return specializations.get(0);
    }

}
