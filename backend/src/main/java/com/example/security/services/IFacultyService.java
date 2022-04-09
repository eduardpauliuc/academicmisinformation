package com.example.security.services;

import com.example.models.Faculty;

import java.util.List;
import java.util.Optional;

public interface IFacultyService {

    List<Faculty> getAllFaculties();

    Faculty saveFaculty(Faculty faculty);

    void deleteFacultyById(Long id);

    Optional<Faculty> findFacultyById(Long id);

}
