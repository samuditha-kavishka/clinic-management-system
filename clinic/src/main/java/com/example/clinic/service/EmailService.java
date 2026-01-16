package com.example.clinic.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String toEmail, String fullName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String htmlContent = "<h1>Welcome to Clinic Management System</h1>" +
                    "<p>Dear " + fullName + ",</p>" +
                    "<p>Your account has been created successfully!</p>";

            helper.setTo(toEmail);
            helper.setSubject("Welcome to Clinic Management System");
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public void sendAppointmentConfirmation(String toEmail, String patientName,
                                            String doctorName, LocalDateTime dateTime) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String htmlContent = "<h1>Appointment Confirmation</h1>" +
                    "<p>Dear " + patientName + ",</p>" +
                    "<p>Your appointment with Dr. " + doctorName + " has been scheduled.</p>" +
                    "<p>Date & Time: " + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "</p>";

            helper.setTo(toEmail);
            helper.setSubject("Appointment Confirmation - Clinic Management System");
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send appointment confirmation email", e);
        }
    }

    public void sendAppointmentUpdate(String toEmail, String patientName,
                                      String status, LocalDateTime dateTime) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String htmlContent = "<h1>Appointment " + status + "</h1>" +
                    "<p>Dear " + patientName + ",</p>" +
                    "<p>Your appointment scheduled on " +
                    dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                    " has been " + status.toLowerCase() + ".</p>";

            helper.setTo(toEmail);
            helper.setSubject("Appointment " + status + " - Clinic Management System");
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send appointment update email", e);
        }
    }
}