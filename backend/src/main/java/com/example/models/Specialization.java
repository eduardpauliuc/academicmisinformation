package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.*;

@Entity
@Table(name = "specializations")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Specialization extends BaseEntity{
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @Column(name = "name")
    private String name;

    @Column(name = "study_language")
    private String studyLanguage;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "chief_of_department_id")
    private Teacher teacher;


}
