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

    @GetMapping("/test/users")
    public String testUsers() {
        StringBuilder result = new StringBuilder();
        result.append("<h1>Users in Database</h1>");

        List<User> users = userRepository.findAll();
        result.append("<p>Total users: ").append(users.size()).append("</p>");

        for (User user : users) {
            result.append("<hr>");
            result.append("<p><strong>Username:</strong> ").append(user.getUsername()).append("</p>");
            result.append("<p><strong>Password (hashed):</strong> ").append(user.getPassword()).append("</p>");
            result.append("<p><strong>Email:</strong> ").append(user.getEmail()).append("</p>");
            result.append("<p><strong>Role:</strong> ").append(user.getRole()).append("</p>");
            result.append("<p><strong>Enabled:</strong> ").append(user.isEnabled()).append("</p>");

            // Test password matching
            String testPassword = "admin123";
            boolean passwordMatches = passwordEncoder.matches(testPassword, user.getPassword());
            result.append("<p><strong>Password '").append(testPassword).append("' matches:</strong> ").append(passwordMatches).append("</p>");
        }

        return result.toString();
    }

    @GetMapping("/test/create-admin")
    public String createAdmin() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@clinic.com");
            admin.setFullName("Administrator");
            admin.setRole(User.Role.ADMIN);
            admin.setEnabled(true);
            userRepository.save(admin);
            return "Admin user created!";
        }
        return "Admin user already exists!";
    }
}