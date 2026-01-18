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
        createUserIfNotExists("admin", "admin123", "admin@clinic.com",
                "System Administrator", User.Role.ADMIN);

        createUserIfNotExists("doctor", "doctor123", "doctor@clinic.com",
                "Dr. John Smith", User.Role.DOCTOR);

        createUserIfNotExists("patient", "patient123", "patient@clinic.com",
                "Jane Doe", User.Role.PATIENT);

        createUserIfNotExists("staff", "staff123", "staff@clinic.com",
                "Robert Johnson", User.Role.STAFF);
    }

    private void createUserIfNotExists(String username, String password,
                                       String email, String fullName, User.Role role) {
        if (userRepository.findByUsername(username).isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setFullName(fullName);
            user.setRole(role);
            user.setEnabled(true);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);

            userRepository.save(user);
            System.out.println("✓ User created: " + username + " (" + role + ")");
        }
    }
}