package com.example.repositories;

import com.example.models.OptionalPreference;
import com.example.models.OptionalPreferenceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOptionalPreferenceRepository extends JpaRepository<OptionalPreference, OptionalPreferenceId> {
}
