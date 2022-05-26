package com.example.payload.responses;

import com.example.models.Grade;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherGradeDTO {
    private Long studentId;
    private String studentName;
    private Integer grade;

    public TeacherGradeDTO(Grade grade){
        this.studentId = grade.getStudent().getId();
        this.studentName = grade.getStudent().getAccount().getFirstName() + " " + grade.getStudent().getAccount().getLastName();
        this.grade = grade.getGrade();
    }
}
