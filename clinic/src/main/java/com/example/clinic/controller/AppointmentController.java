package com.example.clinic.controller;

import com.example.clinic.entity.Appointment;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Patient;
import com.example.clinic.repository.DoctorRepository;
import com.example.clinic.repository.PatientRepository;
import com.example.clinic.service.AppointmentService;
import com.example.clinic.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public String listAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status,
            @AuthenticationPrincipal UserDetails currentUser,
            Model model) {

        String username = currentUser.getUsername();
        Page<Appointment> appointmentPage = appointmentService.getAppointmentsForUser(username, page, size, status);

        model.addAttribute("appointments", appointmentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", appointmentPage.getTotalPages());
        model.addAttribute("status", status);
        return "appointment/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<Doctor> doctors = doctorRepository.findAll();
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("doctors", doctors);
        return "appointment/form";
    }

    @PostMapping("/create")
    public String createAppointment(
            @ModelAttribute Appointment appointment,
            @RequestParam Long doctorId,
            @AuthenticationPrincipal UserDetails currentUser,
            Model model) {

        try {
            String username = currentUser.getUsername();
            Patient patient = patientRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Patient not found: " + username));

            Doctor doctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

            appointment.setPatient(patient);
            appointment.setDoctor(doctor);
            appointment.setDateTime(LocalDateTime.now());
            appointment.setStatus(Appointment.Status.PENDING);

            Appointment savedAppointment = appointmentService.save(appointment);

            // Send confirmation email
            emailService.sendAppointmentConfirmation(
                    patient.getEmail(),
                    patient.getFullName(),
                    doctor.getFullName(),
                    savedAppointment.getDateTime()
            );

            return "redirect:/appointments?success=Appointment created successfully";
        } catch (Exception e) {
            model.addAttribute("error", "Error creating appointment: " + e.getMessage());
            List<Doctor> doctors = doctorRepository.findAll();
            model.addAttribute("doctors", doctors);
            return "appointment/form";
        }
    }

    @PostMapping("/{id}/confirm")
    public String confirmAppointment(@PathVariable Long id) {
        appointmentService.confirmAppointment(id);
        return "redirect:/appointments?success=Appointment confirmed";
    }

    @PostMapping("/{id}/cancel")
    public String cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return "redirect:/appointments?success=Appointment cancelled";
    }

    @PostMapping("/{id}/delete")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments?success=Appointment deleted";
    }

    // Admin view - all appointments
    @GetMapping("/admin")
    public String viewAllAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model) {
        Page<Appointment> appointmentPage = appointmentService.getAllAppointments(page, size);
        model.addAttribute("appointments", appointmentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", appointmentPage.getTotalPages());
        return "appointment/admin-list";
    }
}