package com.example.controllers;

import com.example.security.services.IStudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StudentController {

    private final IStudentService studentService;


}
