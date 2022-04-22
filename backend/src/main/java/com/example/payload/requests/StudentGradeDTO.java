package com.example.payload.requests;

public class StudentGradeDTO {
    private final Long studentId;
    private final Double average;

    public StudentGradeDTO(Long studentId, Double average) {
        this.studentId = studentId;
        this.average = average;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Double getAverage() {
        return average;
    }
}