package com.example.clinic.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    private String notes;

    public enum Status {
        PENDING, CONFIRMED, CANCELLED, COMPLETED
    }
}