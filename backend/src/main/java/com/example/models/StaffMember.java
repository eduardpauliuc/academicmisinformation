package com.example.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "staff_members")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffMember {

    @Id
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    public StaffMember(Account account) {
        this.account = account;
    }

}
