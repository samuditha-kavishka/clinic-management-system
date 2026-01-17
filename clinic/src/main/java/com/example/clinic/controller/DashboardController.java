package com.example.clinic.controller;

import com.example.clinic.service.AppointmentService;
import com.example.clinic.service.DoctorService;
import com.example.clinic.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDate;

@Controller
public class DashboardController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("pageTitle", "Dashboard");

        // Get today's appointments count
        long todayAppointments = appointmentService
                .getAppointmentsByDoctor(1L, LocalDate.now()) // Example doctor ID
                .size();

        // Get total counts
        long totalDoctors = doctorService.getAllDoctors().size();
        long totalPatients = patientService.getAllPatients().size();

        model.addAttribute("todayAppointments", todayAppointments);
        model.addAttribute("totalDoctors", totalDoctors);
        model.addAttribute("totalPatients", totalPatients);

        return "dashboard";
    }

    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/dashboard";
    }
}