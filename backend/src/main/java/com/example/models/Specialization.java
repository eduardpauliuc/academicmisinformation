package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "specializations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @OneToOne
    @JoinColumn(name = "chief_of_department_id", unique = true)
    private Teacher chiefOfDepartment;

    @ManyToOne
    @JoinColumn(name = "degree_id", nullable = false)
    private Degree degree;

    @OneToMany(mappedBy = "specialization")
    private List<Contract> contracts;

    @OneToMany(mappedBy = "specialization")
    private List<Course> courses;

    @OneToMany(mappedBy = "specialization")
    private List<OptionalProposal> proposals;

    @Column(name = "name")
    private String name;

    @Column(name = "study_language")
    private String studyLanguage;

    @Column(name = "letter_identifier", nullable = false, unique = true)
    private String letterIdentifier;

    @Column(name = "semesters")
    private Integer semesters;
}
