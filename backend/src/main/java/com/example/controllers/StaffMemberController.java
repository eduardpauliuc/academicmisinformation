package com.example.controllers;

import com.example.payload.requests.StudentGradeDTO;
import com.example.services.IStudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/staff")
public class StaffMemberController {
    private final IStudentService studentService;

    @GetMapping("/students")
    public List<StudentGradeDTO> getStudents(){
        var students = studentService.getAllStudents();
        var studentGradeDTOs = new LinkedList<StudentGradeDTO>();
        students.forEach(
                student -> {
                    var average = studentService.computeAverageOfLatestSemester(student);
                    if (average != -1)
                        studentGradeDTOs.add(new StudentGradeDTO(student, average));
                }
        );
        return studentGradeDTOs;
    }
}
