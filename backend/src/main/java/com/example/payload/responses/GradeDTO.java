package com.example.payload.responses;

import com.example.models.Grade;

public class GradeDTO {

    private final String courseName;
    private final Integer grade;
    private final Integer semester;

    public GradeDTO(Grade grade, Integer semester) {
        this.courseName = grade.getCourse().getName();
        this.grade = grade.getGrade();
        this.semester = semester;
    }

    public String getCourseName() {
        return courseName;
    }

    public Integer getGrade() {
        return grade;
    }

    public Integer getSemester() {
        return semester;
    }
}
