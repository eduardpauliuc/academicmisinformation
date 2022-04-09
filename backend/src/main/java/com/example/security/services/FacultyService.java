package com.example.security.services;

import com.example.models.Faculty;
import com.example.repositories.IFacultyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FacultyService implements IFacultyService{

    private final IFacultyRepository facultyRepository;

    @Override
    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    @Override
    public Faculty saveFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFacultyById(Long id) {
        facultyRepository.deleteById(id);
    }

    @Override
    public Optional<Faculty> findFacultyById(Long id) {
        return facultyRepository.findById(id);
    }
}
