package com.example.payload.responses;

import com.example.models.Course;
import com.example.models.EStatus;
import com.example.models.OptionalProposal;
import com.example.models.Status;

public class CourseDTO {

    private final String name;
    private final String specializationName;
    private final Integer credits;
    private final String description;
    private final Integer semesterNumber;
    private final Boolean isOptional;
    private final Integer maximumStudentsNumber;
    private final EStatus status;

    public CourseDTO(Course course) {
        this.name = course.getName();
        this.specializationName = course.getSpecialization().getName();
        this.credits = course.getCredits();
        this.description = course.getDescription();
        this.semesterNumber = course.getSemesterNumber();
        this.maximumStudentsNumber = course.getMaximumStudentsNumber();
        this.isOptional = course.getIsOptional();
        this.status = null;
    }

    public CourseDTO(OptionalProposal optional) {
        this.name = optional.getName();
        this.specializationName = optional.getSpecialization().getName();
        this.credits = optional.getCredits();
        this.description = optional.getDescription();
        this.semesterNumber = optional.getSemesterNumber();
        this.maximumStudentsNumber = optional.getMaximumStudentsNumber();
        this.isOptional = Boolean.TRUE;
        this.status = optional.getStatus().getName();
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

    public Boolean getIsOptional() {
        return isOptional;
    }

    public Integer getMaximumStudentsNumber() {
        return maximumStudentsNumber;
    }

    public EStatus getStatus() {
        return status;
    }
}
