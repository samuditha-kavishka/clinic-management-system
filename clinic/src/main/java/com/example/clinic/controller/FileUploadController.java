package com.example.clinic.controller;

import com.example.clinic.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/upload")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        try {
            String fileUrl = fileUploadService.saveFile(file);
            redirectAttributes.addFlashAttribute("message",
                    "File uploaded successfully: " + fileUrl);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Upload failed: " + e.getMessage());
        }
        return "redirect:/appointments";
    }
}