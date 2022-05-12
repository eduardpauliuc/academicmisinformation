package com.example.payload.responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RankingDTO {
    private final String teacherName;
    private final Integer rank;
}
