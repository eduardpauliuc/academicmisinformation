package com.example.controllers;

import com.example.services.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdministratorController {

    private final AdministratorService administratorService;

}
