package com.example.payload.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentGradeDTO {

    private Long studentId;

    private Double average;

    private String name;

    private String group;

}
