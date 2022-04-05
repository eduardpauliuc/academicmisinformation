package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subjects")
@Entity
@Getter
@Setter
public class Subject extends BaseEntity{
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;

    @Column(name = "name")
    private String name;

    @Column(name = "credits")
    private Integer credits;

    @Column(name = "description")
    private String description;

    @Column(name = "semester_number")
    private Integer semesterNumber;

    @Column(name = "is_optional")
    private boolean is_optional;

    @Column(name = "minimum_followers_required")
    private Integer minimumFollowersRequired;
}
