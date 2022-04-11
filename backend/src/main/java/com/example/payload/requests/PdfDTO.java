package com.example.payload.requests;

import com.example.models.Faculty;
import com.example.models.Specialization;

public class PdfDTO {


    private final Specialization specialization;
    private final Integer semester;

    public PdfDTO( Specialization specialization, Integer semester) {

        this.specialization = specialization;
        this.semester = semester;
    }


    public Specialization getSpecialization() {
        return specialization;
    }

    public Integer getSemester() {
        return semester;
    }
}
