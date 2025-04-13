package com.ToDoListApp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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


    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("exist", "Email already exists!");
            return "register";
        }

        userService.createUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
        // wouldnt it make sense to return the custom url created? cause you're creating the account and sending them to their created dashboard
        /*      session.setAttribute("email", email);

                // Build the redirect URL using first and last name
                String customURL = "/" + user.getFirstName().toLowerCase() + "_" + user.getLastName().toLowerCase() + "/dashboard";
                return "redirect:" + customURL;
	*/
        return "login";
    }

    @GetMapping("/{username}/dashboard")
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


	    //if(user.getTasks.size() == 0 ){
	    //aka first time or unlucky all deleted, get tasks from database and put them into the class item. This way no need to access database everytime, as long as we delete/create/modify all tasks on the database and the class item at the same time. Saves time i think?
	    //}


            model.addAttribute("user", user);
            model.addAttribute("tasks", user.getTasks());
            return "dashboard";
            
        }
    }





        // // Parse username back into first and last name
        // String[] names = username.split("_");
        // if (names.length != 2) return "error"; // fallback error view
        // System.out.println("Dashboard route hit for: " + username);

        // String firstName = names[0];
        // String lastName = names[1];

        // // Find user by first and last name (make sure you have this query method)
        // Optional<User> userOpt = userRepository.findByFirstNameAndLastName(firstName, lastName);
        // Optional<User> userEmail = userRepository.findByEmail()
        // if (userOpt.isPresent()) {
        //     User user = userOpt.get();
        //     List<Task> tasks = user.getTasks();  // Assuming `getTasks()` returns user's tasks

        //     model.addAttribute("user", user);
        //     model.addAttribute("tasks", tasks);
        //     return "dashboard";
        // }
        // else {
        //     System.out.println("No user found with that name.");
        // }

        System.out.println("ERROR!");
        return "error"; // User not found

    }

}