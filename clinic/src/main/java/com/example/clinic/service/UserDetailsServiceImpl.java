package com.example.clinic.service;

import com.example.clinic.entity.User;
import com.example.clinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("=== DEBUG: Loading user by username: " + username + " ===");

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("ERROR: User not found: " + username);
                    return new UsernameNotFoundException("User not found: " + username);
                });

        System.out.println("Found user: " + user.getUsername());
        System.out.println("User password (hashed): " + user.getPassword());
        System.out.println("User enabled: " + user.isEnabled());
        System.out.println("User role: " + user.getRole());
        System.out.println("=== DEBUG END ===");

        return user;
    }
}