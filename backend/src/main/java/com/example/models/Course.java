package com.example.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "course")
    private List<OptionalPreference> optionalPreferences;

    @OneToMany(mappedBy = "course")
    private List<Grade> grades;

    @ManyToMany
    @JoinTable(
            name = "teacher_courses",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "teacher_id")}
    )
    private List<Teacher> teachers;

    @ManyToOne
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "credits", nullable = false)
    private Integer credits;

    @Column(name = "description")
    private String description;

    @Column(name = "semester_number", nullable = false)
    private Integer semesterNumber;

    @Column(name = "is_optional", nullable = false)
    private Boolean isOptional;

    @Column(name = "maximum_students_number")
    private Integer maximumStudentsNumber;

}
