package com.example.payload.requests;

import com.example.models.Student;

public class StudentGradeDTO {
    private final Student student;
    private final Double average;

    public StudentGradeDTO(Student student, Double average) {
        this.student = student;
        this.average = average;
    }

    public Student getStudent() {
        return student;
    }

    public Double getAverage() {
        return average;
    }
}