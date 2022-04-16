package com.example.payload.requests;

import com.example.models.Faculty;
import com.example.models.Specialization;
import com.example.models.Student;

public class PdfDTO {

    private final Student student;
    private final Specialization specialization;
    private final Integer semester;

    public PdfDTO( Student student, Specialization specialization, Integer semester) {

        this.student = student;
        this.specialization = specialization;
        this.semester = semester;
    }

    public Student getStudent() {
        return student;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public Integer getSemester() {
        return semester;
    }
}
