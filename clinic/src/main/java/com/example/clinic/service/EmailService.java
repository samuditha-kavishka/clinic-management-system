package com.example.clinic.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendAppointmentConfirmation(String toEmail, String patientName, String doctorName,
                                            String appointmentDate, String appointmentTime) {
        // Email sending logic (to be implemented)
        System.out.println("Sending appointment confirmation email to: " + toEmail);
        System.out.println("Appointment details: " + patientName + " with Dr. " + doctorName +
                " on " + appointmentDate + " at " + appointmentTime);
    }

    public void sendAppointmentReminder(String toEmail, String patientName, String appointmentDate,
                                        String appointmentTime) {
        System.out.println("Sending appointment reminder to: " + toEmail);
    }
}