package com.filevault.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.filevault.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService service) {
        this.userService = service;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam String username,
                                   @RequestParam String password,
                                   Model model) {
        userService.registerUser(username, password, "ROLE_USER");
        model.addAttribute("success", true);
        return "register";
    }

    // @GetMapping("/dashboard")
    // public String dashboard() {
    //     return "dashboard";
    // }
}