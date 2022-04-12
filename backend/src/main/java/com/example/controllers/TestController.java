package com.example.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content";
    }

    @GetMapping("/student")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMINISTRATOR')")
    public String studentAccess() {
        return "Student Board";
    }

    @GetMapping("/teacher")
    @PreAuthorize("hasRole('TEACHER') or  hasRole('CHIEF') or hasRole('ADMINISTRATOR')")
    public String teacherAccess() {
        return "Teacher Board";
    }

    @GetMapping("/chief")
    @PreAuthorize("hasRole('CHIEF') or hasRole('ADMINISTRATOR')")
    public String chiefAccess() {
        return "Chief Board";
    }

    @GetMapping("/staff")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMINISTRATOR')")
    public String staffAccess() {
        return "Staff Board";
    }

    @GetMapping("/administrator")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public String administratorAccess() {
        return "Administrator Board";
    }

}
