package com.example.clinic.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }
        return "home";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        model.addAttribute("role", role);
        model.addAttribute("username", userDetails.getUsername());
        return "dashboard";
    }
}