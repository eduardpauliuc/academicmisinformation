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
import org.slf4j.Logger;
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
    private Logger logger;

    @Autowired
    PasswordEncoder encoder;


    @GetMapping("/faculties")
    public List<FacultyDTO> getAllFaculties() {
        logger.info("Getting all faculties");
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
        logger.info("Updating profile with ID " + profileInformationDTO.getId());
        Account account = accountService.findAccountById(profileInformationDTO.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Account not found.")
        );

        account.setFirstName(profileInformationDTO.getFirstName());
        account.setLastName(profileInformationDTO.getLastName());
        account.setBirthDate(Date.valueOf(profileInformationDTO.getBirthDate()));

        String newPassword = profileInformationDTO.getNewPassword();
        if (newPassword.length() >= 8)
            account.setPasswordDigest(encoder.encode(newPassword));

        logger.info("Saving account with the new data");
        accountService.saveAccount(account);
    }

    @GetMapping("/{facultyId}/specializations")
    public List<SpecializationDTO> getFacultySpecializations(@PathVariable("facultyId") Long facultyId) {
        logger.info("Getting all specializations for the faculty with id " + facultyId);
        Faculty faculty = facultyService.findFacultyById(facultyId).orElseThrow(
                () -> {
                    logger.warn("Faculty not found");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Faculty not found.");
                }
        );

        return faculty.getSpecializations()
                .stream()
                .map(SpecializationDTO::new)
                .collect(Collectors.toList());
    }
}
