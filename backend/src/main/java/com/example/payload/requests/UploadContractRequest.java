package com.example.payload.requests;

import org.springframework.web.multipart.MultipartFile;

public class UploadContractRequest {

    private final Long specializationId;
    private final Integer semester;
    private final MultipartFile file;


    public UploadContractRequest(Long specializationId, Integer semester, MultipartFile file) {
        this.specializationId = specializationId;
        this.semester = semester;
        this.file = file;
    }

    public Long getSpecializationId() {
        return specializationId;
    }

    public Integer getSemester() {
        return semester;
    }

    public MultipartFile getFile() {
        return file;
    }
}
