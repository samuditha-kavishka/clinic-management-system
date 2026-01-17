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
        // Check and create admin user
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // ENCRYPTED!
            admin.setEmail("admin@clinic.com");
            admin.setFullName("Administrator");
            admin.setRole(User.Role.ADMIN);
            admin.setEnabled(true);
            userRepository.save(admin);
            System.out.println("✓ Admin user created: username=admin, password=admin123");
        } else {
            System.out.println("✓ Admin user already exists");
        }

        // Check and create doctor user
        if (userRepository.findByUsername("doctor").isEmpty()) {
            User doctor = new User();
            doctor.setUsername("doctor");
            doctor.setPassword(passwordEncoder.encode("doctor123"));
            doctor.setEmail("doctor@clinic.com");
            doctor.setFullName("Dr. John Smith");
            doctor.setRole(User.Role.DOCTOR);
            doctor.setEnabled(true);
            userRepository.save(doctor);
            System.out.println("✓ Doctor user created: username=doctor, password=doctor123");
        }

        // Check and create patient user
        if (userRepository.findByUsername("patient").isEmpty()) {
            User patient = new User();
            patient.setUsername("patient");
            patient.setPassword(passwordEncoder.encode("patient123"));
            patient.setEmail("patient@clinic.com");
            patient.setFullName("John Doe");
            patient.setRole(User.Role.PATIENT);
            patient.setEnabled(true);
            userRepository.save(patient);
            System.out.println("✓ Patient user created: username=patient, password=patient123");
        }

        System.out.println("Total users in database: " + userRepository.count());
    }
}