package com.example.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "faculties")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Faculty extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "website")
    private String website;

}
