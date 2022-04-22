package com.example.payload.responses;

public class OptionalPreferenceDTO {

    private final CourseDTO course;
    private final Integer rank;

    public OptionalPreferenceDTO(CourseDTO course, Integer rank) {

        this.course = course;
        this.rank = rank;
    }


    public CourseDTO getCourse() {
        return course;
    }

    public Integer getRank() {
        return rank;
    }
}
