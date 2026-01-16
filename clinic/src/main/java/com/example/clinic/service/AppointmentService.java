package com.example.clinic.service;

import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.User;
import com.example.clinic.repository.AppointmentRepository;
import com.example.clinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public Page<Appointment> getAppointmentsForUser(String username, int page, int size, String status) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Pageable pageable = PageRequest.of(page, size, Sort.by("dateTime").descending());

        if (status != null && !status.isEmpty()) {
            try {
                Appointment.Status statusEnum = Appointment.Status.valueOf(status.toUpperCase());
                return appointmentRepository.findByUserAndStatus(username, statusEnum, pageable);
            } catch (IllegalArgumentException e) {
                return appointmentRepository.findByUser(username, pageable);
            }
        }

        return appointmentRepository.findByUser(username, pageable);
    }

    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Appointment getById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
    }

    public void confirmAppointment(Long id) {
        Appointment appointment = getById(id);
        appointment.setStatus(Appointment.Status.CONFIRMED);
        appointmentRepository.save(appointment);

        if (appointment.getPatient() != null && appointment.getPatient().getEmail() != null) {
            emailService.sendAppointmentUpdate(
                    appointment.getPatient().getEmail(),
                    appointment.getPatient().getFullName(),
                    "Confirmed",
                    appointment.getDateTime()
            );
        }
    }

    public void cancelAppointment(Long id) {
        Appointment appointment = getById(id);
        appointment.setStatus(Appointment.Status.CANCELLED);
        appointmentRepository.save(appointment);

        if (appointment.getPatient() != null && appointment.getPatient().getEmail() != null) {
            emailService.sendAppointmentUpdate(
                    appointment.getPatient().getEmail(),
                    appointment.getPatient().getFullName(),
                    "Cancelled",
                    appointment.getDateTime()
            );
        }
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}