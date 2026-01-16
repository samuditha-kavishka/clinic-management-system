package com.example.clinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("pageTitle", "Dashboard");
        model.addAttribute("welcomeMessage", "Welcome to Clinic Management System");
        return "dashboard";
    }

    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/dashboard";
    }
}