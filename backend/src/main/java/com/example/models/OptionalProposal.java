package com.example.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "optional_proposals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OptionalProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name  = "specialization_id")
    private Specialization specialization;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @Column(name = "comments")
    private String comments;

    @Column(name = "maximum_students_number", nullable = false)
    private Integer maximumStudentsNumber;

    @Column(name = "credits", nullable = false)
    private Integer credits;

    @Column(name = "description")
    private String description;

    @Column(name = "semester_number", nullable = false)
    private Integer semesterNumber;

    @Column(name = "name", nullable = false)
    private String name;


}
