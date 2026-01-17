package com.example.clinic.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.Period;

@Data
@Entity
@Table(name = "patients")
@PrimaryKeyJoinColumn(name = "user_id")
public class Patient extends User {

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Transient
    private Integer age;

    private String gender;

    @Column(name = "blood_group")
    private String bloodGroup;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    public Integer getAge() {
        if (dateOfBirth != null) {
            return Period.between(dateOfBirth, LocalDate.now()).getYears();
        }
        return null;
    }
}