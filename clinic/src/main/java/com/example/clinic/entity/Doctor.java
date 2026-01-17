package com.example.clinic.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "patients")
@PrimaryKeyJoinColumn(name = "user_id")
public class Patient extends User {

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private Integer age;
    private String gender;

    @Column(name = "blood_group")
    private String bloodGroup;

    private String address;
    private String phone;

    @Column(name = "emergency_contact")
    private String emergencyContact;
}