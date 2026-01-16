package com.example.clinic.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendWelcomeEmail(String toEmail, String fullName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            Context context = new Context();
            context.setVariable("fullName", fullName);
            String htmlContent = templateEngine.process("email/welcome", context);

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

            Context context = new Context();
            context.setVariable("patientName", patientName);
            context.setVariable("doctorName", doctorName);
            context.setVariable("appointmentTime",
                    dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

            String htmlContent = templateEngine.process("email/appointment-confirmation", context);

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

            Context context = new Context();
            context.setVariable("patientName", patientName);
            context.setVariable("status", status);
            context.setVariable("appointmentTime",
                    dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

            String htmlContent = templateEngine.process("email/appointment-update", context);

            helper.setTo(toEmail);
            helper.setSubject("Appointment " + status + " - Clinic Management System");
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send appointment update email", e);
        }
    }
}