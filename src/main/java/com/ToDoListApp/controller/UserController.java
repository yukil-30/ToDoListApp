package com.ToDoListApp.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ToDoListApp.entity.Task;
import com.ToDoListApp.entity.User;
import com.ToDoListApp.repository.UserRepository;
import com.ToDoListApp.service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
public class UserController {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public UserService userService;
    
    
    @GetMapping({"/", "/home"}) // just generic should lead to home page
    public String homePage(Model model) {
        model.addAttribute("message", "To get started, please log in or create your account!"); //simply first test of giving a item for the html
        return "home";  // Resolves to /src/main/resources/templates/home.html
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        return "register";
    }
    
    @GetMapping("/login")
	public String login() {
		return "login";
	}

    // once the login form completed, post via form complete sign in
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

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("exist", "Email already exists!");
            return "register";
        }

        userService.createUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
        return "login";
    }

    @GetMapping("/{username}/dashboard") //going to the dashboard (main part of the site)
    public String showDashboard(@PathVariable String username, HttpSession session, Model model) {

        String email = (String) session.getAttribute("email");

        if (email != null) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {

            User user = userOpt.get();

            // Double-check the path variable matches the logged-in user
            String expectedUsername = user.getFirstName().toLowerCase() + "_" + user.getLastName().toLowerCase();

            if (!username.equals(expectedUsername)) {
                model.addAttribute("error", "Unauthorized access");
                return "error";
            }

            //temporary lists to divide the user tasks
            List<Task> late = new ArrayList<>(); 
            List<Task> ontime = new ArrayList<>(); 
            int size = user.getTasks().size();
            
            
            for (int i = 0; i < size; ++i) {
            	if(user.getTasks().get(i).getDueDate().isBefore(LocalDate.now())) {//due date already passed
            		late.add(user.getTasks().get(i));
            	}
            	else {//due date not passed
            		ontime.add(user.getTasks().get(i));
            	}
            }
            model.addAttribute("user", user);
            model.addAttribute("tasks", ontime);
            model.addAttribute("latetasks", late);
            return "dashboard";
            
        }
    }





        System.out.println("ERROR!");
        return "error"; // User not found

    }

}