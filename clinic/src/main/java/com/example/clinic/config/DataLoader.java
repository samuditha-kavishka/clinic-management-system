package com.example.clinic.config;

import com.example.clinic.entity.User;
import com.example.clinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Checking and creating users ===");

        // Create admin user if not exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@clinic.com");
            admin.setFullName("Administrator");
            admin.setRole(User.Role.ADMIN);
            admin.setEnabled(true);
            userRepository.save(admin);
            System.out.println("✓ Admin user created: admin/admin123");
        } else {
            System.out.println("✓ Admin user already exists");
        }

        // Create doctor user if not exists
        if (userRepository.findByUsername("doctor").isEmpty()) {
            User doctor = new User();
            doctor.setUsername("doctor");
            doctor.setPassword(passwordEncoder.encode("doctor123"));
            doctor.setEmail("doctor@clinic.com");
            doctor.setFullName("Dr. John Smith");
            doctor.setRole(User.Role.DOCTOR);
            doctor.setEnabled(true);
            userRepository.save(doctor);
            System.out.println("✓ Doctor user created: doctor/doctor123");
        } else {
            System.out.println("✓ Doctor user already exists");
        }

        // Create patient user if not exists
        if (userRepository.findByUsername("patient").isEmpty()) {
            User patient = new User();
            patient.setUsername("patient");
            patient.setPassword(passwordEncoder.encode("patient123"));
            patient.setEmail("patient@clinic.com");
            patient.setFullName("John Doe");
            patient.setRole(User.Role.PATIENT);
            patient.setEnabled(true);
            userRepository.save(patient);
            System.out.println("✓ Patient user created: patient/patient123");
        } else {
            System.out.println("✓ Patient user already exists");
        }

        System.out.println("=== User creation complete ===");
        System.out.println("Total users in database: " + userRepository.count());
    }
}