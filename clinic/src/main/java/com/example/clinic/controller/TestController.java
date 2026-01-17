package com.example.clinic.controller;

import com.example.clinic.entity.User;
import com.example.clinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/test/db")
    public String testDatabase() {
        StringBuilder result = new StringBuilder();
        result.append("<h1>Database Test</h1>");

        // Count users
        long count = userRepository.count();
        result.append("<p>Total users in database: ").append(count).append("</p>");

        // List all users
        List<User> users = userRepository.findAll();
        for (User user : users) {
            result.append("<hr>");
            result.append("<p><strong>Username:</strong> ").append(user.getUsername()).append("</p>");
            result.append("<p><strong>Email:</strong> ").append(user.getEmail()).append("</p>");
            result.append("<p><strong>Role:</strong> ").append(user.getRole()).append("</p>");
            result.append("<p><strong>Enabled:</strong> ").append(user.isEnabled()).append("</p>");
        }

        return result.toString();
    }

    @GetMapping("/test/create-test-user")
    public String createTestUser() {
        if (userRepository.findByUsername("test").isEmpty()) {
            User testUser = new User();
            testUser.setUsername("test");
            testUser.setPassword(passwordEncoder.encode("test123"));
            testUser.setEmail("test@clinic.com");
            testUser.setFullName("Test User");
            testUser.setRole(User.Role.PATIENT);
            testUser.setEnabled(true);
            userRepository.save(testUser);
            return "Test user created: test/test123";
        }
        return "Test user already exists";
    }
}