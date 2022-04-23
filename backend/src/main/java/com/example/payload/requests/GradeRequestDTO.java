package com.example.payload.requests;

public class GradeRequestDTO{

    private final Integer grade;

    public Integer getGrade() {
        return grade;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public GradeRequestDTO(Integer grade, Long studentId, Long courseId) {
        this.grade = grade;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    private final Long studentId;
    private final Long courseId;


}
