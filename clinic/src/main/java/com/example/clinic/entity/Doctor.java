package com.example.clinic.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "doctors")
@PrimaryKeyJoinColumn(name = "user_id")
public class Doctor extends User {

    private String specialization;
    private String qualification;

    @Column(name = "license_number", unique = true)
    private String licenseNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "room_number")
    private String roomNumber;
}