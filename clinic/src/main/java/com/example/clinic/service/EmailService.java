package com.example.clinic.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    /**
     * Send welcome email to new user
     */
    public void sendWelcomeEmail(String toEmail, String fullName) {
        // For development - just print to console
        System.out.println("============================================");
        System.out.println("SENDING WELCOME EMAIL");
        System.out.println("To: " + toEmail);
        System.out.println("Subject: Welcome to Clinic Management System!");
        System.out.println("Body:");
        System.out.println("Dear " + fullName + ",");
        System.out.println("Welcome to Clinic Management System!");
        System.out.println("Your account has been created successfully.");
        System.out.println("============================================");

        // In production, you would use:
        // 1. JavaMailSender
        // 2. SendGrid API
        // 3. AWS SES
        // 4. Other email service
    }

    /**
     * Send appointment confirmation email
     */
    public void sendAppointmentConfirmation(String toEmail, String patientName,
                                            String doctorName, String appointmentDate,
                                            String appointmentTime) {
        System.out.println("Sending appointment confirmation to: " + toEmail);
        System.out.println("Patient: " + patientName);
        System.out.println("Doctor: " + doctorName);
        System.out.println("Date: " + appointmentDate);
        System.out.println("Time: " + appointmentTime);
    }

    /**
     * Send appointment reminder
     */
    public void sendAppointmentReminder(String toEmail, String patientName,
                                        String appointmentDate, String appointmentTime) {
        System.out.println("Sending appointment reminder to: " + toEmail);
        System.out.println("Patient: " + patientName);
        System.out.println("Date: " + appointmentDate);
        System.out.println("Time: " + appointmentTime);
    }
}