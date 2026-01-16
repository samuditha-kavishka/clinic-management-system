package com.example.clinic.repository;

import com.example.clinic.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByDoctorId(Long doctorId);

    List<Appointment> findByDoctorIdAndAppointmentDate(Long doctorId, LocalDate date);

    Optional<Appointment> findByDoctorIdAndAppointmentTime(Long doctorId, LocalDateTime time);

    List<Appointment> findByStatus(String status);  // e.g. "PENDING", "CONFIRMED", "CANCELLED", "COMPLETED"

    List<Appointment> findByAppointmentDateBetween(LocalDate startDate, LocalDate endDate);

    // Upcoming appointments example
    List<Appointment> findByAppointmentTimeAfterAndStatusOrderByAppointmentTimeAsc(
            LocalDateTime now, String status);
}