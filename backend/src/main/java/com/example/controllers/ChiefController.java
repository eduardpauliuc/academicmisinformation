package com.example.controllers;

import com.example.models.Course;
import com.example.models.OptionalProposal;
import com.example.models.Specialization;
import com.example.models.Teacher;
import com.example.payload.requests.OptionalReviewDTO;
import com.example.payload.responses.CourseDTO;
import com.example.payload.responses.RankingDTO;
import com.example.payload.responses.TeacherDTO;
import com.example.services.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final ITeacherService teacherService;

    @Autowired
    private Logger logger;

    @GetMapping("/optionals")
    @PreAuthorize("hasRole('CHIEF')")
    public List<CourseDTO> getAllOptionals(@PathVariable("id") Long id) {
        logger.info("Getting all optionals of teacher with id " + id);
        Teacher chief = chiefService.findTeacherById(id).orElseThrow(
                () -> {
                    logger.warn("Chief not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Chief not found.");
                });
        logger.info("Optionals successfully retrieved!");
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
        logger.info("Reviewing optional with the ID " + optionalId);
        OptionalProposal optionalProposal = optionalProposalService.findOptionalProposalById(optionalId).orElseThrow(
                () -> {
                    logger.warn("Optional proposal not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Optional proposal not found.");
                }
        );

        if (optionalReviewDTO.getStatus()) {
            logger.info("Accepting the optional with the id " + optionalId);
            Course acceptedOptional = new Course(optionalProposal);
            courseService.saveCourse(acceptedOptional);
            optionalProposalService.deleteOptionalProposal(optionalId);
            logger.info("Optional accepted successfully!");
        } else {
            logger.info("Rejecting the optional with the id " + optionalId);
            optionalProposal.setComments(optionalReviewDTO.getReviewMessage());
            optionalProposal.setStatus(statusService.getRejectedStatus());
            optionalProposalService.saveOptionalProposal(optionalProposal);
            logger.info("Optional rejected successfully with the message " + optionalReviewDTO.getReviewMessage());
        }
    }

    @GetMapping("/teachers/disciplines/{teacherId}")
    @PreAuthorize("hasRole('CHIEF')")
    public List<CourseDTO> getDisciplinesForTeacher(@PathVariable("id") Long id,
                                                    @PathVariable("teacherId") Long teacherId) {
        logger.info("Getting disciplines for teacher with id " + teacherId);
        Teacher teacher = chiefService.findTeacherById(teacherId).orElseThrow(
                () -> {
                    logger.warn("Teacher not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Teacher not found");
                }
        );
        Specialization specialization = getSpecializationOfChief(id);
        if (!specialization.getChiefOfDepartment().getId().equals(id)) {
            logger.warn("Invalid chief of department provided!");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid chief");
        }
        List<CourseDTO> acceptedCourses = teacher.getCourses().stream()
                .filter(course -> course.getSpecialization().equals(specialization))
                .map(CourseDTO::new)
                .collect(Collectors.toList());

        List<CourseDTO> notYetAcceptedCourses = teacher.getOptionalProposals().stream()
                .map(CourseDTO::new)
                .collect(Collectors.toList());

        acceptedCourses.addAll(notYetAcceptedCourses);
        logger.info("All courses returned successfully!");
        return acceptedCourses;
    }

    @GetMapping("/teachers/rankings")
    @PreAuthorize("hasRole('CHIEF')")
    public List<RankingDTO> getTeacherRankings(@PathVariable("id") Long id) {
        logger.info("Getting the teacher rankings for chief with id " + id);
        Specialization specialization = getSpecializationOfChief(id);
        Map<Teacher, Double> averages = chiefService.getAveragesForTeachers(specialization);
        logger.info("Ordering the averages of the teachers descendingly");
        List<Teacher> orderedTeachers = averages.keySet().stream()
                .sorted(Comparator.comparingDouble(t -> -averages.get(t)))
                .collect(Collectors.toList());

        List<RankingDTO> dtos = new LinkedList<>();
        for (int i = 0; i < orderedTeachers.size(); i++) {
            Teacher currentTeacher = orderedTeachers.get(i);
            logger.info("Added on position " + i + " teacher with id " + currentTeacher.getId());
            String fullName = currentTeacher.getAccount().getFirstName() + " " + currentTeacher.getAccount().getLastName();
            dtos.add(new RankingDTO(fullName, i));
        }
        return dtos;
    }

    @GetMapping("/teachers")
    @PreAuthorize("hasRole('CHIEF')")
    public List<TeacherDTO> getTeachers(@PathVariable("id") Long id) {
        Specialization specialization = getSpecializationOfChief(id);

        return teacherService.getAllTeachers()
                .stream()
                .filter(
                        teacher -> teacher.getCourses()
                                .stream()
                                .anyMatch(course -> course.getSpecialization().equals(specialization))
                )
                .map(
                        teacher -> new TeacherDTO(
                                teacher.getId(),
                                teacher.getAccount().getFirstName() + " " + teacher.getAccount().getLastName()
                        )
                )
                .collect(Collectors.toList());
    }


    private Specialization getSpecializationOfChief(Long id) {
        List<Specialization> specializations =
                specializationService.getAllSpecializations()
                        .stream()
                        .filter(
                                specialization -> specialization.getChiefOfDepartment() != null &&
                                        id.equals(specialization.getChiefOfDepartment().getId())
                        )
                        .toList();

        if (specializations.isEmpty()) {
            logger.warn("There is no specialization that the teacher is the chief of!");
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "There is no specialization that the teacher is the chief of"
            );
        }

        return specializations.get(0);
    }
}
