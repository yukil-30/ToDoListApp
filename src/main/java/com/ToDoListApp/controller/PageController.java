package com.ToDoListApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import ch.qos.logback.core.model.Model;

@Controller
public class PageController {

    @GetMapping("/")
    public String showHomePage(Model model) {
        // Add a 'name' attribute to the model
        // model.
        model.addAttribute("name", "Christine");
        return "index";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }
        
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }


}