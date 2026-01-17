package com.example.clinic.controller;

import com.example.clinic.dto.UserRegistrationDto;
import com.example.clinic.entity.User;
import com.example.clinic.service.EmailService;
import com.example.clinic.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/login")
    public String showLoginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "registered", required = false) String registered,
            Model model) {

        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password!");
        }

        if (logout != null) {
            model.addAttribute("successMessage", "You have been logged out successfully.");
        }

        if (registered != null) {
            model.addAttribute("successMessage", "Registration successful! Please login.");
        }

        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserRegistrationDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String processRegistration(
            @Valid @ModelAttribute("userDto") UserRegistrationDto userDto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        // Check password confirmation
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            model.addAttribute("errorMessage", "Passwords do not match");
            return "auth/register";
        }

        try {
            // Register user
            User user = userService.registerUser(userDto);

            // Send welcome email
            emailService.sendWelcomeEmail(user.getEmail(), user.getFullName());

            redirectAttributes.addFlashAttribute("successMessage",
                    "Registration successful! Please login with your credentials.");
            return "redirect:/auth/login?registered";

        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/register";
        }
    }
}