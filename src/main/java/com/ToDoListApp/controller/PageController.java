/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.ToDoListApp.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class PageController {

    @GetMapping("/login")
	public String login() {
		return "login";
	}

    @GetMapping({"/", "/home"}) //right now haven't planned it out too much but just generic localhost should lead to home page
    public String homePage(Model model) {
        model.addAttribute("message", "Welcome to the Home Page!");
        return "home";  // Resolves to /src/main/resources/templates/home.html
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        return "register";
    }

}
