package com.example.clinic.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Doctor extends User {

    private String specialization;
    private String qualification;

    private LocalTime availableFrom;
    private LocalTime availableTo;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();
}