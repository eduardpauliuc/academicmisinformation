package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    @Id
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "title_id")
    private Title title;

    @ManyToMany(mappedBy = "teachers")
    private List<Course> courses;

    @OneToMany(mappedBy = "teacher")
    private List<OptionalProposal> optionalProposals;

    public Teacher(Account account) {
        this.account = account;
    }

}
