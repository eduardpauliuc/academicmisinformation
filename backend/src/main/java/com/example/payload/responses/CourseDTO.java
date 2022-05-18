package com.example.payload.responses;

import com.example.models.Course;
import com.example.models.EStatus;
import com.example.models.OptionalProposal;

public class CourseDTO {

    private final Long id;

    private final String name;

    private final String specializationName;

    private final Integer credits;

    private final String description;

    private final Integer semesterNumber;

    private final Boolean isOptional; // null for CourseDTOs converted from OptionalProposal

    private final Integer maximumStudentsNumber; // null for CourseDTOs converted from Course that was not an optional

    private final EStatus status; // null for CourseDTOs converted from Course


    public CourseDTO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.specializationName = course.getSpecialization().getName();
        this.credits = course.getCredits();
        this.description = course.getDescription();
        this.semesterNumber = course.getSemesterNumber();
        this.isOptional = course.getIsOptional();
        this.maximumStudentsNumber = course.getMaximumStudentsNumber();
        this.status = null;
    }

    public CourseDTO(OptionalProposal optionalProposal) {
        this.id = optionalProposal.getId();
        this.name = optionalProposal.getName();
        this.specializationName = optionalProposal.getSpecialization().getName();
        this.credits = optionalProposal.getCredits();
        this.description = optionalProposal.getDescription();
        this.semesterNumber = optionalProposal.getSemesterNumber();
        this.isOptional = null;
        this.maximumStudentsNumber = optionalProposal.getMaximumStudentsNumber();
        this.status = optionalProposal.getStatus().getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecializationName() {
        return specializationName;
    }

    public Integer getCredits() {
        return credits;
    }

    public String getDescription() {
        return description;
    }

    public Integer getSemesterNumber() {
        return semesterNumber;
    }

    public Boolean getOptional() {
        return isOptional;
    }

    public Integer getMaximumStudentsNumber() {
        return maximumStudentsNumber;
    }

    public EStatus getStatus() {
        return status;
    }
}
