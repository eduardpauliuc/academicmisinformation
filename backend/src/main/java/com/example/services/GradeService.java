package com.example.services;

import com.example.models.Grade;
import com.example.payload.requests.GradeRequestDTO;
import com.example.repositories.ICourseRepository;
import com.example.repositories.IGradeRepository;
import com.example.repositories.IStudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GradeService implements IGradeService {
    private IGradeRepository gradeRepository;
    private IStudentRepository studentRepository;
    private ICourseRepository courseRepository;

    @Override
    public Grade saveGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    @Override
    public Grade convertToGrade(GradeRequestDTO gradeRequestDTO) {
        if (gradeRequestDTO.getGrade() <= 0) {
            throw new RuntimeException("Grade cannot be less or equal to 0.");
        }

        // controller layer throws exceptions if the student or the course do not exist
        return new Grade(
                null,
                gradeRequestDTO.getGrade(),
                this.studentRepository.getById(gradeRequestDTO.getStudentId()),
                this.courseRepository.getById(gradeRequestDTO.getCourseId())
        );
    }
}
