package com.example.clinic.repository;

import com.example.clinic.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUsername(String username);
    List<Doctor> findBySpecialization(String specialization);
}