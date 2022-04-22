package com.example.payload.responses;

import com.example.models.Specialization;

public class SpecializationDTO {

    private final Long id;
    private final Long facultyId;
    private final String name;
    private final String degreeType;
    private final Integer numberOfSemesters;

    public SpecializationDTO(Specialization specialization) {
        this.id = specialization.getId();
        this.facultyId = specialization.getFaculty().getId();
        this.name = specialization.getName();
        this.degreeType = specialization.getDegree().getName();
        this.numberOfSemesters = specialization.getSemesters();
    }

    public Long getId() {
        return id;
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public String getName() {
        return name;
    }

    public String getDegreeType() {
        return degreeType;
    }

    public Integer getNumberOfSemesters() {
        return numberOfSemesters;
    }
}
