package com.example.services;

import com.example.models.Specialization;
import com.example.models.Student;

public interface IOptionalPreferenceService {
    void removePreferencesForStudent(Student student, Specialization specialization);
}
