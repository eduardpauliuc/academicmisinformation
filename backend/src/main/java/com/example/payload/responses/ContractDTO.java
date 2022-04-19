package com.example.payload.responses;

import com.example.models.Contract;
import com.example.models.Specialization;

import java.sql.Date;

public class ContractDTO {
    private final Long studentId;
    private final SpecializationDTO specialization;
    private final Date startDate;
    private final Date endDate;
    private final Integer semester;

    public ContractDTO( Contract contract) {
        this.studentId = contract.getStudent().getId();
        this.specialization = new SpecializationDTO(contract.getSpecialization());
        this.startDate = contract.getStartDate();
        this.endDate = contract.getEndDate();
        this.semester = contract.getSemesterNumber();
    }

    public Long getStudentId() {
        return studentId;
    }

    public SpecializationDTO getSpecialization() {
        return specialization;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Integer getSemester() {
        return semester;
    }
}
