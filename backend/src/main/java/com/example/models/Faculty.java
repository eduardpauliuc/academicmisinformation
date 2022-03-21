package com.example.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "faculties")
public class Faculty {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "website")
    private String website;

    public Faculty() {
    }

    public Faculty(Long id, String name, String website) {
        this.id = id;
        this.name = name;
        this.website = website;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
