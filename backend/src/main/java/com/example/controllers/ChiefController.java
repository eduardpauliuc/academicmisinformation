package com.example.controllers;

import com.example.models.Course;
import com.example.models.OptionalProposal;
import com.example.models.Teacher;
import com.example.payload.requests.OptionalReviewDTO;
import com.example.payload.responses.CourseDTO;
import com.example.services.IChiefService;
import com.example.services.ICourseService;
import com.example.services.IOptionalProposalService;
import com.example.services.IStatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/chief/{id}")
public class ChiefController {

    private final IChiefService chiefService;
    private final IOptionalProposalService optionalProposalService;
    private final ICourseService courseService;
    private final IStatusService statusService;

    @GetMapping("/optionals")
    @PreAuthorize("hasRole('CHIEF')")
    public List<CourseDTO> getAllOptionals(@PathVariable("id") Long id) {
        Teacher chief = chiefService.findTeacherById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Chief not found.")
        );

        return chiefService.getAllOptionalsBySpecialization(chief.getSpecialization());
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
}
