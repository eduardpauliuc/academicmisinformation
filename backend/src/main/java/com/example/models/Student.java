package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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

    // gets the highest semester on a contract for a specialization
    // ideally, this should be the current semester of the current student
    // returns -1 if the student has no contracts for the given specialization
    public Integer getSemester(Specialization specialization){
        return contracts.stream()
                .filter(contract -> contract.getSpecialization() == specialization)
                .map(Contract::getSemesterNumber)
                .max(Integer::compareTo)
                .orElse(-1);
    }

    // gets the latest contract for a given specialization
    // representing the contract signed for the highest semester for a specialization
    // or an empty Optional if there are none
    public Optional<Contract> getLatestContract(Specialization specialization){
        Integer semester = getSemester(specialization);
        if (semester == -1)
            return Optional.empty();
        return contracts.stream().findAny().filter(contract -> contract.getSpecialization().equals(specialization)
                        && contract.getSemesterNumber().equals(semester));
    }

    // computes the average of the student for a specialization and a semester
    // will usually be used together with getSemester() to compute the average
    // for the most recent semester
    // if there are any ungraded courses or if there are no courses at all,
    // the average is discarded, and we return -1
    // otherwise, we compute the pondered mean
    public double findAverageForSemester(Specialization specialization, Integer semester){
        AtomicBoolean areFullyGraded = new AtomicBoolean(true);
        AtomicInteger creditsSum = new AtomicInteger(0);
        AtomicInteger gradesSum = new AtomicInteger(0);
        var grades = getGrades()
                .stream().filter(grade -> grade.getCourse().getSemesterNumber().equals(semester)
                        && grade.getCourse().getSpecialization().equals(specialization));
        grades.forEach(
                grade ->{
                    creditsSum.addAndGet(grade.getCourse().getCredits());
                    if (grade.getGrade() == null)
                        areFullyGraded.set(false);
                    else
                        gradesSum.addAndGet(grade.getCourse().getCredits() * grade.getGrade());
                }
        );

        if (!areFullyGraded.get() || gradesSum.get() == 0)
            return -1;
        return (double) gradesSum.get() / (double) creditsSum.get();
    }

}
