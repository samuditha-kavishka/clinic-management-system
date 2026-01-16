package com.example.clinic.service;

import com.example.clinic.entity.Patient;
import com.example.clinic.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient findByUsername(String username) {
        return patientRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Patient not found with username: " + username));
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }
}