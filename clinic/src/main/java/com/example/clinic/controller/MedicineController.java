package com.example.clinic.controller;

import com.example.clinic.entity.Medicine;
import com.example.clinic.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping
    public String getAllMedicines(Model model) {
        List<Medicine> medicines = medicineService.getAllMedicines();
        model.addAttribute("medicines", medicines);
        return "medicines/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("medicine", new Medicine());
        return "medicines/create";
    }

    @PostMapping("/create")
    public String createMedicine(@ModelAttribute Medicine medicine) {
        medicineService.createMedicine(medicine);
        return "redirect:/medicines";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Medicine medicine = medicineService.getMedicineById(id);
        model.addAttribute("medicine", medicine);
        return "medicines/edit";
    }

    @PostMapping("/update/{id}")
    public String updateMedicine(@PathVariable Long id, @ModelAttribute Medicine medicine) {
        medicineService.updateMedicine(id, medicine);
        return "redirect:/medicines";
    }

    @GetMapping("/delete/{id}")
    public String deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return "redirect:/medicines";
    }
}