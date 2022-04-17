package com.example.services;

import com.example.models.Grade;
import com.example.repositories.IGradeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GradeService implements IGradeService{
    private IGradeRepository gradeRepository;

    @Override
    public Grade saveGrade(Grade grade) {
        return gradeRepository.save(grade);
    }
}
