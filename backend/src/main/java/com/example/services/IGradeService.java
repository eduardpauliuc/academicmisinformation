package com.example.services;

import com.example.models.Grade;
import com.example.payload.requests.GradeRequestDTO;

public interface IGradeService {

    Grade saveGrade(Grade grade);

    Grade convertToGrade(GradeRequestDTO gradeRequestDTO);

}
