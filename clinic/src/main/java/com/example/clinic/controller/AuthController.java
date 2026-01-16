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

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("userDto", new UserRegistrationDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserRegistrationDto userDto,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "auth/register";
        }

        if (userService.existsByUsername(userDto.getUsername())) {
            model.addAttribute("usernameError", "Username already exists");
            return "auth/register";
        }

        User user = userService.registerUser(userDto);

        // Send welcome email
        emailService.sendWelcomeEmail(user.getEmail(), user.getFullName());

        return "redirect:/auth/login?registered";
    }
}