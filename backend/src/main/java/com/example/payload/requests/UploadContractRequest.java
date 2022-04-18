package com.example.payload.requests;
import org.springframework.web.multipart.MultipartFile;
public class UploadContractRequest {

    private final Long facultyId;
    private final Long specializationId;
    private final Integer semester;


    public UploadContractRequest(Long facultyId, Long specializationId, Integer semester) {
        this.facultyId = facultyId;
        this.specializationId = specializationId;
        this.semester = semester;
    }


    public Long getFacultyId() {
        return facultyId;
    }

    public Long getSpecializationId() {
        return specializationId;
    }

    public Integer getSemester() {
        return semester;
    }

}
