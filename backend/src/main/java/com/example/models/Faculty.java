package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "faculties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "faculty")
    private List<Specialization> specializations;

    @OneToMany(mappedBy = "faculty")
    private List<StaffMember> staffMembers;

}
