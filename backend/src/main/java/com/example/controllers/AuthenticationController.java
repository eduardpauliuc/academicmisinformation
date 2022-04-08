package com.example.controllers;

import com.example.models.Account;
import com.example.models.ERole;
import com.example.models.Role;
import com.example.payload.requests.LoginRequest;
import com.example.payload.requests.SignupRequest;
import com.example.payload.responses.JwtResponse;
import com.example.payload.responses.MessageResponse;
import com.example.security.jwt.JwtUtils;
import com.example.security.services.AccountDetails;
import com.example.security.services.IAccountService;
import com.example.security.services.IRoleService;
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
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
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


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = this.jwtUtils.generateJwtToken(authentication);

        AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();
        List<String> roles = accountDetails.getAuthorities().stream()
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
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) throws ParseException {
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

        // the format of the date must be 'yyyy-MM-dd'
        Date date = Date.valueOf(signupRequest.getBirthDate());

        Account account = new Account(
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()),
                signupRequest.getFirstName(),
                signupRequest.getLastName(),
                date
        );

        String requestRole = signupRequest.getRole();
        Role role;

        switch (requestRole) {
            case "staff":
                Role staffRole = this.roleService.findByName(ERole.ROLE_STAFF)
                        .orElseThrow(() -> new RuntimeException("Error: Role not found!"));
                role = staffRole;
                break;

            case "teacher":
                Role teacherRole = this.roleService.findByName(ERole.ROLE_TEACHER)
                        .orElseThrow(() -> new RuntimeException("Error: Role not found!"));
                role = teacherRole;
                break;

            case "administrator":
                Role administratorRole = this.roleService.findByName(ERole.ROLE_ADMINISTRATOR)
                        .orElseThrow(() -> new RuntimeException("Error: Role not found!"));
                role = administratorRole;
                break;

            default:
                Role studentRole = this.roleService.findByName(ERole.ROLE_STUDENT)
                        .orElseThrow(() -> new RuntimeException("Error: Role not found!"));
                role = studentRole;
                break;
        }

        account.setRole(role);
        this.accountService.save(account);

        return ResponseEntity.ok(new MessageResponse("Account successfully created!"));
    }

}
