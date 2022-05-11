package com.example.payload.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FacultyDTO {

    private Long facultyId;

    private String name;

    private List<SpecializationDTO> specializations;

}
