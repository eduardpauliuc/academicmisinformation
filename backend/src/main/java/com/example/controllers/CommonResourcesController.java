package com.example.controllers;

import com.example.payload.responses.FacultyDTO;
import com.example.payload.responses.SpecializationDTO;
import com.example.services.IFacultyService;
import com.example.services.ISpecializationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/common")
public class CommonResourcesController {

    private final IFacultyService facultyService;
    private final ISpecializationService specializationService;

    @GetMapping("/faculties")
    public List<FacultyDTO> getAllFaculties() {
        return facultyService.getAllFaculties()
                .stream()
                .map(faculty -> {
                    List<SpecializationDTO> specializations = faculty.getSpecializations()
                            .stream()
                            .map(specializationService::convertToSpecializationDTO)
                            .collect(Collectors.toList());

                    return new FacultyDTO(
                            faculty.getId(),
                            faculty.getName(),
                            specializations
                    );
                })
                .collect(Collectors.toList());
    }
}
