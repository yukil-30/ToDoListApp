

package com.ToDoListApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ToDoListApp.entity.User;

@Controller
public class UserController {

    // private final UserRepository userRepository;
    // private final PasswordEncoder passwordEncoder;
    // private final UserService userService;

    // public UserController(UserRepository userRepository, UserService userService, PasswordEncoder passwordEncoder) {
    //     this.userRepository = userRepository;
    //     this.userService = userService;
    //     this.passwordEncoder = passwordEncoder;
    // }



    // @GetMapping("/register")
    // public String showRegistrationForm(Model model) {
    //     model.addAttribute("user", new User());
    //     System.out.println("Created an empty user to ready for storing info");
    //     return "register";

    // }




    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        // if (userRepository.findByEmail(user.getEmail()).isPresent()) {
        //     model.addAttribute("error", "Email already exists!");
        //     return "register"; // Reload register page with error
        // }

        // System.out.println("No such email found so create User now");

        // // CASE: email not found, create account
        
        // userService.registerUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());

        // System.out.println("Created user and have been added to database");
        
        // model.addAttribute("message", "Registration successful! You can now login.");
        // return "redirect:/login"; // Redirect to login page after registration

        System.out.println(user.toString());
        return "index";
    }


    // @PostMapping("/login")
    // public String loginUser(@RequestParam String email, @RequestParam String password, Model model) {
    //     Optional<User> userOpt = userRepository.findByEmail(email);

    //     if (userOpt.isPresent()) {
    //         User user = userOpt.get();
    //         if (passwordEncoder.matches(password, user.getPassword())) {
    //             model.addAttribute("full-name", user.toString());
    //             return "dashboard"; // Redirect to dashboard
    //         }
    //     }

    //     model.addAttribute("error", "Invalid credentials!");
    //     return "login"; // Reload login page with error
    // }

}
