package com.example.payload.requests;

public class OptionalProposalDTO {

    private final Long teacherId;
    private final Long specializationId;
    private final String name;
    private final Integer credits;
    private final String description;
    private final Integer semesterNumber;
    private final Integer maximumStudentsNumber;

    public OptionalProposalDTO(Long teacherId, Long specializationId, String name, Integer credits, String description, Integer semesterNumber, Integer maximumStudentsNumber) {
        this.teacherId = teacherId;
        this.specializationId = specializationId;
        this.name = name;
        this.credits = credits;
        this.description = description;
        this.semesterNumber = semesterNumber;
        this.maximumStudentsNumber = maximumStudentsNumber;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public Long getSpecializationId() {
        return specializationId;
    }

    public String getName() {
        return name;
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

    public Integer getMaximumStudentsNumber() {
        return maximumStudentsNumber;
    }
}
