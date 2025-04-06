/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.ToDoListApp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ToDoListApp.entity.User;
import com.ToDoListApp.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;

    // Inside your login controller
    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                
                session.setAttribute("email", email);

                // Build the redirect URL using first and last name
                String customURL = "/" + user.getFirstName().toLowerCase() + "_" + user.getLastName().toLowerCase() + "/dashboard";
                return "redirect:" + customURL;
            }
        }

        model.addAttribute("error", "Invalid credentials!");
        return "login";
    }


}
