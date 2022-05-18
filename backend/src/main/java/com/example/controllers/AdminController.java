package com.example.controllers;

import com.example.models.*;
import com.example.payload.requests.NewAccountDTO;
import com.example.payload.responses.JwtResponse;
import com.example.services.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    PasswordEncoder encoder;

    private Logger logger;

    private final IAccountService accountService;
    private final IRoleService roleService;
    private IStudentService studentService;
    private ITeacherService teacherService;
    private IStaffMemberService staffMemberService;

    @GetMapping("/accounts")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public List<JwtResponse> getAllAccounts() {
        logger.info("Getting all accounts as Admin");
        return accountService.getAllAccounts()
                .stream()
                .map(account -> new JwtResponse(
                        null,
                        account.getId(),
                        account.getUsername(),
                        account.getEmail(),
                        account.getRole().getName().name(),
                        account.getFirstName(),
                        account.getLastName(),
                        account.getBirthDate()
                ))
                .collect(Collectors.toList());
    }

    @PostMapping("/accounts")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public void createAccount(@RequestBody NewAccountDTO newAccountDTO) {
        logger.info("Creating a new account with username " + newAccountDTO.getUsername()
            +", e-mail " + newAccountDTO.getEmail() + " and role " + newAccountDTO.getRole());
        if (accountService.existsByUsername(newAccountDTO.getUsername())) {
            logger.warn("User with username " + newAccountDTO.getUsername() + " already exists!");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Duplicate username.");
        }

        if (accountService.existsByEmail(newAccountDTO.getEmail())) {
            logger.warn("User with e-mail " + newAccountDTO.getEmail() + " already exists!");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Duplicate email.");
        }

        ERole eRole = ERole.valueOf(newAccountDTO.getRole().trim().toUpperCase(Locale.ROOT));
        Role role = roleService.findByName(eRole).orElseThrow(
                () -> {
                    logger.warn("Role not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Role not found.");
                });

        // create account
        Account account = new Account(
                newAccountDTO.getUsername(),
                newAccountDTO.getEmail(),
                encoder.encode(newAccountDTO.getPassword()),
                role
        );

        logger.info("Saving account...");
        account = accountService.saveAccount(account);
        logger.info("Account successfully saved!");

        // create account owner
        switch (eRole) {
            case ROLE_STAFF:
                StaffMember staffMember = new StaffMember(account);
                staffMemberService.saveStaffMember(staffMember);
                logger.info("Account added to staff members!");
                break;

            case ROLE_STUDENT:
                String registrationNumber = studentService.generateUniqueRegistrationNumber();
                Student student = new Student(account, registrationNumber);
                studentService.saveStudent(student);
                logger.info("Account added to students!");
                break;

            case ROLE_TEACHER:
            case ROLE_CHIEF:
                Teacher teacher = new Teacher(account);
                teacherService.saveTeacher(teacher);
                logger.info("Account added to teachers!");
                break;

            default:
                logger.info("Requested an invalid role!");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!");
        }
    }

    @DeleteMapping("/accounts/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public void deleteAccount(@PathVariable Long id) {
        logger.info("Deleting account with id " + id);
        Account account = accountService.findAccountById(id).orElseThrow(
                () -> {
                    logger.warn("Account not found!");
                    return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Account not found.");
                });

        switch (account.getRole().getName()) {
            case ROLE_STAFF: {
                StaffMember owner = account.getStaffMember();
                logger.info("Deleting staff member with id " + owner.getId());
                staffMemberService.deleteStaffMemberById(owner.getId());
                break;
            }
            case ROLE_STUDENT: {
                Student owner = account.getStudent();
                logger.info("Deleting student with id " + owner.getId());
                studentService.deleteStudentById(owner.getId());
                break;
            }
            case ROLE_TEACHER:
            case ROLE_CHIEF: {
                Teacher owner = account.getTeacher();
                logger.info("Deleting teacher with id " + owner.getId());
                teacherService.deleteTeacherById(owner.getId());
                break;
            }
            default: {
                logger.info("Something went wrong!");
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!");
            }
        }

        accountService.deleteAccountById(account.getId());
        logger.info("Account with id " + id + " successfully deleted!");
    }
}
