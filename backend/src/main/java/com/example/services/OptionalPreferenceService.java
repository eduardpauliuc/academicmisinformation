package com.example.services;

import com.example.models.OptionalPreference;
import com.example.models.Specialization;
import com.example.models.Student;
import com.example.repositories.IOptionalPreferenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OptionalPreferenceService implements IOptionalPreferenceService {

    private IOptionalPreferenceRepository optionalPreferenceRepository;

    @Override
    public void removePreferencesForStudent(Student student, Specialization specialization) {
        var optionalsToRemove = optionalPreferenceRepository.findAll()
                .stream()
                .filter(optional -> optional.getStudent().equals(student) && optional.getCourse().getSpecialization().equals(specialization))
                .collect(Collectors.toList());
        optionalPreferenceRepository.deleteAll(optionalsToRemove);
    }

    @Override
    public OptionalPreference saveOptionalPreference(OptionalPreference optionalPreference) {
        return optionalPreferenceRepository.save(optionalPreference);
    }
}
