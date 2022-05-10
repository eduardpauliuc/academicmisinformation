package com.example.payload.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentOptionalsRankingDTO {

    private Long optionalId; // optional is of type Course

    private Integer index;

}
