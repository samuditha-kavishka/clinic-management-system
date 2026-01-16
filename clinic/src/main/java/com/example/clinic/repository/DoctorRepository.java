package com.example.clinic.repository;

import com.example.clinic.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByRegistrationNumber(String registrationNumber);

    Optional<Doctor> findByEmail(String email);

    List<Doctor> findBySpecializationContainingIgnoreCase(String specialization);

    List<Doctor> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName);

    List<Doctor> findByActiveTrue();
}