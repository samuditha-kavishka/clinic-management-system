package com.example.clinic.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
@PrimaryKeyJoinColumn(name = "user_id")
@Data
public class Patient extends User {

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "blood_group")
    private String bloodGroup;

    private String address;
    private String phone;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    public enum Gender {
        MALE, FEMALE, OTHER
    }
}