package com.example.controllers;

import com.example.models.Contract;
import com.example.models.Student;
import com.example.services.IStudentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/student/{id}")
public class StudentController {

    private final IStudentService studentService;


    @GetMapping("/contracts")
    public List<Contract> getStudentsContracts(@PathVariable("id") Long id){
        Optional<Student> student = studentService.findStudentById(id);
        return student.map(Student::getContracts).orElse(null);
//        ArrayList<Contract> contracts = new ArrayList<>();
//        contracts.add(new Contract());
//        return contracts;
    }

}
