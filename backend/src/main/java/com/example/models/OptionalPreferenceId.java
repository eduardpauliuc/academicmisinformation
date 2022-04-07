package com.example.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OptionalPreferenceId implements Serializable {

    @Column(name = "student_id", nullable = false)
    private Long student_id;

    @Column(name = "course_id", nullable = false)
    private Long course_id;

    public OptionalPreferenceId() {}

    public OptionalPreferenceId(Long student_id, Long course_id) {
        this.student_id = student_id;
        this.course_id = course_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionalPreferenceId that = (OptionalPreferenceId) o;
        return student_id.equals(that.student_id) && course_id.equals(that.course_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student_id, course_id);
    }
}
