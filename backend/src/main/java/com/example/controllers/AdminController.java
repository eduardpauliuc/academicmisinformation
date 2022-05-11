package com.example.controllers;

import com.example.models.*;
import com.example.payload.requests.NewAccountDTO;
import com.example.payload.responses.JwtResponse;
import com.example.services.*;
import lombok.AllArgsConstructor;
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
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    PasswordEncoder encoder;

    private final IAccountService accountService;
    private final IRoleService roleService;
    private IStudentService studentService;
    private ITeacherService teacherService;
    private IStaffMemberService staffMemberService;

    @GetMapping("/accounts")
    @PreAuthorize("hasRole('ADMIN')")
    public List<JwtResponse> getAllAccounts() {
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
    @PreAuthorize("hasRole('ADMIN')")
    public void createAccount(@RequestBody NewAccountDTO newAccountDTO) {
        if (accountService.existsByUsername(newAccountDTO.getUsername())) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Duplicate username.");
        }

        if (accountService.existsByEmail(newAccountDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Duplicate email.");
        }

        ERole eRole = ERole.valueOf("ROLE_" + newAccountDTO.getRole().trim().toUpperCase(Locale.ROOT));
        Role role = roleService.findByName(eRole).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Role not found.")
        );

        // create account
        Account account = new Account(
                newAccountDTO.getUsername(),
                newAccountDTO.getEmail(),
                encoder.encode(newAccountDTO.getPassword()),
                role
        );

        account = accountService.saveAccount(account);

        // create account owner
        switch (eRole) {
            case ROLE_STAFF:
                StaffMember staffMember = new StaffMember(account);
                staffMemberService.saveStaffMember(staffMember);
                break;

            case ROLE_STUDENT:
                String registrationNumber = studentService.generateUniqueRegistrationNumber();
                Student student = new Student(account, registrationNumber);
                studentService.saveStudent(student);
                break;

            case ROLE_TEACHER:
            case ROLE_CHIEF:
                Teacher teacher = new Teacher(account);
                teacherService.saveTeacher(teacher);
                break;

            default:
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!");
        }
    }

    @DeleteMapping("/accounts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAccount(@PathVariable Long id) {
        Account account = accountService.findAccountById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Account not found.")
        );

        switch (account.getRole().getName()) {
            case ROLE_STAFF: {
                StaffMember owner = account.getStaffMember();
                staffMemberService.deleteStaffMemberById(owner.getId());
                break;
            }
            case ROLE_STUDENT: {
                Student owner = account.getStudent();
                studentService.deleteStudentById(owner.getId());
                break;
            }
            case ROLE_TEACHER:
            case ROLE_CHIEF: {
                Teacher owner = account.getTeacher();
                teacherService.deleteTeacherById(owner.getId());
                break;
            }
            default: {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!");
            }
        }

        accountService.deleteAccountById(account.getId());
    }
}
