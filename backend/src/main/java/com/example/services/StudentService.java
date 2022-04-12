package com.example.services;


import com.example.models.Student;
import com.example.repositories.IStudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class StudentService implements IStudentService {

    private final IStudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Optional<Student> findStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public double computeAverageOfLatestSemester(Student student) {
        AtomicInteger maxSemesterNumber = new AtomicInteger(-1);
        AtomicBoolean areFullyGraded = new AtomicBoolean(true);
        AtomicInteger creditsSum = new AtomicInteger(0);
        AtomicInteger gradesSum = new AtomicInteger(0);

        var grades = student.getGrades();
        grades.forEach(
                grade ->{
                    int currentSemester = grade.getCourse().getSemesterNumber();
                    if (currentSemester > maxSemesterNumber.get()){
                        maxSemesterNumber.set(currentSemester);
                        creditsSum.set(grade.getCourse().getCredits());
                        if (grade.getGrade() != null) {
                            gradesSum.set(grade.getGrade() * grade.getCourse().getCredits());
                        }
                        areFullyGraded.set(grade.getGrade() != null);
                    }
                    else{
                        if (currentSemester == maxSemesterNumber.get() && areFullyGraded.get()){
                            creditsSum.addAndGet(grade.getCourse().getCredits());
                            gradesSum.addAndGet(grade.getGrade() * grade.getCourse().getCredits());
                        }
                    }

                }
        );

        if (!areFullyGraded.get() || gradesSum.get() == 0)
            return -1;
        return (double) gradesSum.get() / (double) creditsSum.get();
    }
}
