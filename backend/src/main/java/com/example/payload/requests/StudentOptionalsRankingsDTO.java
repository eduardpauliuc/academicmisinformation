package com.example.payload.requests;

import lombok.Data;

import java.util.List;

@Data
public class StudentOptionalsRankingsDTO {

    private List<StudentOptionalsRankingDTO> optionalIdIndexPairs;

}
