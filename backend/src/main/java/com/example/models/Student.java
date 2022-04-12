package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "registration_number", unique = true)
    private String registrationNumber;

    @OneToMany(mappedBy = "student")
    private List<Grade> grades;

    @OneToMany(mappedBy = "student")
    private List<OptionalPreference> optionalPreferences;

    @OneToMany(mappedBy = "student")
    private List<Contract> contracts;

    public Student(Account account, String registrationNumber) {
        this.account = account;
        this.registrationNumber = registrationNumber;
    }

}
