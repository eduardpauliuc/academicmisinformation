package com.example.controllers;

import com.example.models.*;
import com.example.payload.requests.LoginRequest;
import com.example.payload.requests.SignupRequest;
import com.example.payload.responses.JwtResponse;
import com.example.payload.responses.MessageResponse;
import com.example.security.jwt.JwtUtils;
import com.example.security.security_utils.AccountDetails;
import com.example.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/authenticate")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

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
        if (this.accountService.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username already exists!"));
        }

        if (this.accountService.existsByEmail(signupRequest.getEmail())) {
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
            case "staff":
                role = this.roleService.findByName(ERole.ROLE_STAFF)
                        .orElseThrow(() -> new RuntimeException("Error: Role not found!"));
                break;

            case "teacher":
                role = this.roleService.findByName(ERole.ROLE_TEACHER)
                        .orElseThrow(() -> new RuntimeException("Error: Role not found!"));
                break;

            case "administrator":
                role = this.roleService.findByName(ERole.ROLE_ADMINISTRATOR)
                        .orElseThrow(() -> new RuntimeException("Error: Role not found!"));
                break;

            default:
                role = this.roleService.findByName(ERole.ROLE_STUDENT)
                        .orElseThrow(() -> new RuntimeException("Error: Role not found!"));
        }

        account.setRole(role);
        account = this.accountService.saveAccount(account);

        // create the persons associated with the new account
        switch (account.getRole().getName()) {
            case ROLE_STAFF:
                StaffMember staffMember = new StaffMember(account);
                this.staffMemberService.saveStaffMember(staffMember);
                break;

            case ROLE_STUDENT:
//                String registrationNumber = this.studentService.generateUniqueRegistrationNumber();
                int leftLimit = 48; // numeral '0'
                int rightLimit = 122; // letter 'z'
                int targetStringLength = 10;
                Random random = new Random();
                // TODO: take from student service
                String registrationNumber = random.ints(leftLimit, rightLimit + 1)
                        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                        .limit(targetStringLength)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
                Student student = new Student(account, registrationNumber);
                        this.studentService.saveStudent(student);
                        break;

            case ROLE_TEACHER:
            case ROLE_CHIEF:
                Teacher teacher = new Teacher(account);
                this.teacherService.saveTeacher(teacher);
                break;
        }

        return ResponseEntity.ok(new MessageResponse("Account successfully created!"));
    }

}
