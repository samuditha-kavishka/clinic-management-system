package com.example.clinic.controller;

import com.example.clinic.entity.User;
import com.example.clinic.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public String getAllUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model) {

        log.debug("Fetching users - page: {}, size: {}, sort: {}, direction: {}",
                page, size, sort, direction);

        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        Page<User> userPage = userService.getAllUsers(pageable);

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("totalItems", userPage.getTotalElements());
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);

        // Statistics
        model.addAttribute("totalUsers", userService.countUsers());
        model.addAttribute("adminCount", userService.countUsersByRole(User.Role.ADMIN));
        model.addAttribute("doctorCount", userService.countUsersByRole(User.Role.DOCTOR));
        model.addAttribute("patientCount", userService.countUsersByRole(User.Role.PATIENT));

        return "users/list";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        log.debug("Fetching user by ID: {}", id);

        try {
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
            return "users/view";
        } catch (Exception e) {
            log.error("Error fetching user with ID: {}", id, e);
            model.addAttribute("error", "User not found");
            return "redirect:/users";
        }
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", User.Role.values());
        return "users/create";
    }

    @PostMapping("/create")
    public String createUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        log.info("Creating new user: {}", user.getUsername());

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", User.Role.values());
            return "users/create";
        }

        try {
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("success",
                    "User created successfully: " + user.getUsername());
            return "redirect:/users";
        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage(), e);
            model.addAttribute("error", e.getMessage());
            model.addAttribute("roles", User.Role.values());
            return "users/create";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        log.debug("Loading edit form for user ID: {}", id);

        try {
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
            model.addAttribute("roles", User.Role.values());
            return "users/edit";
        } catch (Exception e) {
            log.error("Error loading user for edit: {}", id, e);
            model.addAttribute("error", "User not found");
            return "redirect:/users";
        }
    }

    @PostMapping("/{id}/edit")
    public String updateUser(
            @PathVariable Long id,
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        log.info("Updating user ID: {}", id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", User.Role.values());
            return "users/edit";
        }

        try {
            userService.updateUser(id, user);
            redirectAttributes.addFlashAttribute("success",
                    "User updated successfully");
            return "redirect:/users";
        } catch (Exception e) {
            log.error("Error updating user: {}", e.getMessage(), e);
            model.addAttribute("error", e.getMessage());
            model.addAttribute("roles", User.Role.values());
            return "users/edit";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        log.info("Deleting user ID: {}", id);

        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("success",
                    "User deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting user: {}", id, e);
            redirectAttributes.addFlashAttribute("error",
                    "Error deleting user: " + e.getMessage());
        }

        return "redirect:/users";
    }

    // AJAX endpoints for real-time operations

    @GetMapping("/check-username")
    @ResponseBody
    public boolean checkUsername(@RequestParam String username) {
        return userService.isUsernameAvailable(username);
    }

    @GetMapping("/check-email")
    @ResponseBody
    public boolean checkEmail(@RequestParam String email) {
        return userService.isEmailAvailable(email);
    }

    @GetMapping("/search")
    @ResponseBody
    public List<User> searchUsers(@RequestParam String query) {
        return userService.searchUsers(query);
    }
}