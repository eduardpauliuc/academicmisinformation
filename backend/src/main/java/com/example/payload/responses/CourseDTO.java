package com.example.payload.responses;

import com.example.models.Course;
import com.example.models.Status;

public class CourseDTO {

    private final String name;
    private final String specializationName;
    private final Integer credits;
    private final String description;
    private final Integer semesterNumber;
    private final Boolean optional;
    private final Integer maximumStudentsNumber;
    private final Status status;

    public CourseDTO(Course course, Boolean optional, Status status) {
        this.name = course.getName();
        this.specializationName = course.getSpecialization().getName();
        this.credits = course.getCredits();
        this.description = course.getDescription();
        this.semesterNumber = course.getSemesterNumber();
        this.maximumStudentsNumber = course.getMaximumStudentsNumber();
        this.optional = optional;
        this.status = status;
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
        return optional;
    }

    public Integer getMaximumStudentsNumber() {
        return maximumStudentsNumber;
    }

    public Status getStatus() {
        return status;
    }
}
