package com.example.clinic.service;

import com.example.clinic.dto.UserRegistrationDto;
import com.example.clinic.entity.User;
import com.example.clinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Transactional
    public User registerUser(UserRegistrationDto userDto) {
        User user;

        switch (userDto.getRole()) {
            case "PATIENT":
                user = new com.example.clinic.entity.Patient();
                break;
            case "DOCTOR":
                user = new com.example.clinic.entity.Doctor();
                break;
            default:
                user = new User() {};
                user.setRole("ROLE_USER");
                break;
        }

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFullName(userDto.getFullName());

        if (user.getRole() == null) {
            user.setRole("ROLE_" + userDto.getRole());
        }

        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
}