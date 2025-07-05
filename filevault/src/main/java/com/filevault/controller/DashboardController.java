package com.filevault.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.filevault.model.FileMetadata;
import com.filevault.model.User;
import com.filevault.repository.UserRepository;
import com.filevault.service.FileService;

@Controller

public class DashboardController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private FileService fileService;

    @GetMapping("/dashboard")
    public String routeToDashboard(Authentication auth) {
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/user/dashboard";
        }
    }

    @GetMapping("/user/dashboard")
    public String userDashboard(Model model, Authentication auth) {
        String username = auth.getName();
        User user = userRepo.findByUsername(username).orElseThrow();
        List<FileMetadata> files = fileService.getUserFiles(user);
        model.addAttribute("files", files);
        return "user_dashboard";
    }

    @PostMapping("/user/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Authentication auth) throws IOException {
        User user = userRepo.findByUsername(auth.getName()).orElseThrow();
        fileService.uploadFile(file, user);
        return "redirect:/user/dashboard";
    }

    @GetMapping("/files/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        return fileService.getFileById(id)
            .map(file -> ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(file.getData()))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        List<FileMetadata> allFiles = fileService.getAllFiles();
        model.addAttribute("files", allFiles);
        return "admin_dashboard";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteFile(@PathVariable Long id) {
        fileService.deleteFileById(id);
        return "redirect:/admin/dashboard";
    }
}