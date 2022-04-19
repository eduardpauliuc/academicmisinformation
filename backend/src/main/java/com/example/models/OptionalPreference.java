package com.example.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "optional_preferences")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class OptionalPreference {

    @EmbeddedId
    private OptionalPreferenceId id;

    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private Course course;

    @Column(name = "rank", nullable = false)
    private Integer rank;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionalPreference that = (OptionalPreference) o;
        return id.equals(that.id) && student.equals(that.student) && course.equals(that.course) && rank.equals(that.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, student, course, rank);
    }
}
