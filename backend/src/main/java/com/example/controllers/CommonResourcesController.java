package com.example.controllers;

import com.example.models.Account;
import com.example.models.Faculty;
import com.example.payload.requests.ProfileInformationDTO;
import com.example.payload.responses.FacultyDTO;
import com.example.payload.responses.SpecializationDTO;
import com.example.services.IAccountService;
import com.example.services.IFacultyService;
import com.example.services.ISpecializationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/common")
public class CommonResourcesController {

    private final IFacultyService facultyService;
    private final ISpecializationService specializationService;
    private final IAccountService accountService;

    @Autowired
    PasswordEncoder encoder;


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

    @PostMapping("/profile")
    public void updateProfile(@RequestBody ProfileInformationDTO profileInformationDTO) {
        Account account = accountService.findAccountById(profileInformationDTO.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Account not found.")
        );

        account.setFirstName(profileInformationDTO.getFirstName());
        account.setLastName(profileInformationDTO.getLastName());
        account.setBirthDate(Date.valueOf(profileInformationDTO.getBirthDate()));
        account.setPasswordDigest(encoder.encode(profileInformationDTO.getNewPassword()));

        accountService.saveAccount(account);
    }

    @GetMapping("/{facultyId}/specializations")
    public List<SpecializationDTO> getFacultySpecializations(@PathVariable("facultyId") Long facultyId) {
        Faculty faculty = facultyService.findFacultyById(facultyId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Faculty not found.")
        );

        return faculty.getSpecializations()
                .stream()
                .map(SpecializationDTO::new)
                .collect(Collectors.toList());
    }
}