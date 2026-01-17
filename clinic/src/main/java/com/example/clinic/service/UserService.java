package com.example.clinic.service;

import com.example.clinic.dto.UserRegistrationDto;
import com.example.clinic.entity.User;
import com.example.clinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ========== PAGINATION METHODS ==========

    /**
     * Get all users WITHOUT pagination
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get all users WITH pagination
     */
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    /**
     * Get user by ID
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    /**
     * Total user count
     */
    public long countUsers() {
        return userRepository.count();
    }

    // ========== REGISTRATION & VALIDATION METHODS ==========

    /**
     * Register a new user from registration form
     */
    public User registerUser(UserRegistrationDto registrationDto) {
        // Validate registration
        validateRegistration(registrationDto);

        // Create user entity
        User user = new User();
        user.setUsername(registrationDto.getUsername().toLowerCase().trim());
        user.setEmail(registrationDto.getEmail().toLowerCase().trim());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setFullName(registrationDto.getFullName().trim());
        user.setRole(User.Role.PATIENT); // Default role for registration
        user.setEnabled(true);

        return userRepository.save(user);
    }

    private void validateRegistration(UserRegistrationDto dto) {
        // Check username availability
        if (existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Check email availability
        if (existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Validate password
        if (dto.getPassword() == null || dto.getPassword().length() < 8) {
            throw new RuntimeException("Password must be at least 8 characters");
        }
    }

    /**
     * Check if username exists
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Check if username is available
     */
    public boolean isUsernameAvailable(String username) {
        return !existsByUsername(username);
    }

    /**
     * Check if email exists
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Check if email is available
     */
    public boolean isEmailAvailable(String email) {
        return !existsByEmail(email);
    }

    // ========== CRUD METHODS ==========

    /**
     * Find user by username
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Find user by email
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Create a user (admin function)
     */
    public User createUser(User user) {
        // Check if username already exists
        if (existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists: " + user.getUsername());
        }

        // Check if email already exists
        if (existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered: " + user.getEmail());
        }

        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Update user
     */
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);

        // Update fields
        user.setFullName(userDetails.getFullName());
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());
        user.setEnabled(userDetails.isEnabled());

        // Update password if provided and not empty
        if (userDetails.getPassword() != null && !userDetails.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        return userRepository.save(user);
    }

    /**
     * Delete user
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    // ========== STATISTICS & SEARCH METHODS ==========

    /**
     * Get users by role
     */
    public List<User> getUsersByRole(User.Role role) {
        return userRepository.findByRole(role);
    }

    /**
     * Count users by role
     */
    public long countUsersByRole(User.Role role) {
        return userRepository.countByRole(role);
    }

    /**
     * Search users by name
     */
    public List<User> searchUsersByName(String name) {
        return userRepository.searchByName(name);
    }

    /**
     * Get users by enabled status
     */
    public List<User> getUsersByEnabledStatus(boolean enabled) {
        return userRepository.findByEnabledStatus(enabled);
    }

    /**
     * Search users (general search)
     */
    public List<User> searchUsers(String query) {
        // Search by name or username
        List<User> byName = userRepository.searchByName(query);

        // Also search by username
        Optional<User> byUsername = userRepository.findByUsername(query);

        // Combine results
        List<User> results = byName;
        byUsername.ifPresent(user -> {
            if (!results.contains(user)) {
                results.add(user);
            }
        });

        return results;
    }
}