package com.example.controllers;

import com.example.models.*;
import com.example.payload.requests.LoginRequest;
import com.example.payload.requests.SignupRequest;
import com.example.payload.responses.JwtResponse;
import com.example.payload.responses.MessageResponse;
import com.example.security.jwt.JwtUtils;
import com.example.security.security_utils.AccountDetails;
import com.example.services.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authenticate")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private Logger logger;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IStudentService studentService;

    @Autowired
    private ITeacherService teacherService;

    @Autowired
    private IStaffMemberService staffMemberService;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("Signing in with username " + loginRequest.getUsername());
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = this.jwtUtils.generateJwtToken(authentication);

        AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();
        List<String> roles = accountDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        Optional<String> roleWrapper = roles.stream().findFirst(); // each user has exactly one role
        String role = roleWrapper.orElse("");
        logger.info("Sign in successful with username " + loginRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(
                jwt,
                accountDetails.getId(),
                accountDetails.getUsername(),
                accountDetails.getEmail(),
                role,
                accountDetails.getFirstName(),
                accountDetails.getLastName(),
                accountDetails.getBirthDate()
        ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        logger.info("Signing up user with username " + signupRequest.getUsername());

        if (this.accountService.existsByUsername(signupRequest.getUsername())) {
            logger.info("User with username " + signupRequest.getUsername() + " already exists!");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username already exists!"));
        }

        if (this.accountService.existsByEmail(signupRequest.getEmail())) {
            logger.info("User with e-mail " + signupRequest.getEmail() + " already exists!");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email already exists!"));
        }

        Account account = new Account(
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()),
                signupRequest.getFirstName(),
                signupRequest.getLastName(),
                Date.valueOf(signupRequest.getBirthDate()) // the format of the date must be 'yyyy-MM-dd'
        );

        String requestRole = signupRequest.getRole();
        Role role;

        switch (requestRole) {
            case "administrator":
                logger.info("Creating administrator account of account with username " + signupRequest.getUsername());
                role = this.roleService.findByName(ERole.ROLE_ADMINISTRATOR)
                        .orElseThrow(() -> new RuntimeException("Error: Role not found!"));
                break;

            case "staff":
                logger.info("Creating staff member account of account with username " + signupRequest.getUsername());
                role = this.roleService.findByName(ERole.ROLE_STAFF)
                        .orElseThrow(() -> new RuntimeException("Error: Role not found!"));
                break;

            case "chief":
                logger.info("Creating chief account of account with username " + signupRequest.getUsername());
                role = this.roleService.findByName(ERole.ROLE_CHIEF)
                        .orElseThrow(() -> new RuntimeException("Error: Role not found!"));
                break;

            case "teacher":
                logger.info("Creating teacher account of account with username " + signupRequest.getUsername());
                role = this.roleService.findByName(ERole.ROLE_TEACHER)
                        .orElseThrow(() -> new RuntimeException("Error: Role not found!"));
                break;

            case "student":
            default:
                logger.info("Creating student account of account with username " + signupRequest.getUsername());
                role = this.roleService.findByName(ERole.ROLE_STUDENT)
                        .orElseThrow(() -> new RuntimeException("Error: Role not found!"));
        }

        account.setRole(role);
        account = this.accountService.saveAccount(account);
        logger.info("Account has been created successfully!");

        // create account associated owner
        switch (account.getRole().getName()) {
            case ROLE_STAFF:
                StaffMember staffMember = new StaffMember(account);
                this.staffMemberService.saveStaffMember(staffMember);
                logger.info("Account added to staff members!");
                break;

            case ROLE_STUDENT:
                String registrationNumber = this.studentService.generateUniqueRegistrationNumber();
                Student student = new Student(account, registrationNumber);
                this.studentService.saveStudent(student);
                logger.info("Account added to students!");
                break;

            case ROLE_TEACHER:
            case ROLE_CHIEF:
                Teacher teacher = new Teacher(account);
                this.teacherService.saveTeacher(teacher);
                logger.info("Account added to teachers!");
                break;
        }

        return ResponseEntity.ok(new MessageResponse("Account successfully created!"));
    }

}
