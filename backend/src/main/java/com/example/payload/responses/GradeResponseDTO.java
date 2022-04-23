package com.example.payload.responses;

import com.example.models.Grade;


public class GradeResponseDTO {

    private final Integer grade;
    private final String courseName;
    private final Integer semester;


    public GradeResponseDTO(Grade grade, Integer semester) {
        this.grade = grade.getGrade();
        this.courseName = grade.getCourse().getName();
        this.semester = semester;
    }

    public Integer getGrade() {
        return grade;
    }

    public String getCourseName() {
        return courseName;
    }

    public Integer getSemester() {
        return semester;
    }
}
