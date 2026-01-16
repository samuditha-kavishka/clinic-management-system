package com.example.clinic.repository;

import com.example.clinic.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.patient.username = :username OR a.doctor.username = :username")
    Page<Appointment> findByUser(@Param("username") String username, Pageable pageable);

    @Query("SELECT a FROM Appointment a WHERE (a.patient.username = :username OR a.doctor.username = :username) AND a.status = :status")
    Page<Appointment> findByUserAndStatus(@Param("username") String username,
                                          @Param("status") Appointment.Status status,
                                          Pageable pageable);

    Page<Appointment> findByPatientUsername(String username, Pageable pageable);
    Page<Appointment> findByDoctorUsername(String username, Pageable pageable);
    Page<Appointment> findByStatus(Appointment.Status status, Pageable pageable);
}