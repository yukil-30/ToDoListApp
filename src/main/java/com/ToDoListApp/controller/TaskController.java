package com.ToDoListApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import com.ToDoListApp.entity.Task;
import com.ToDoListApp.entity.User;
import com.ToDoListApp.repository.TaskRepository;
import com.ToDoListApp.repository.UserRepository;
import com.ToDoListApp.service.TaskService;
import com.ToDoListApp.service.UserService;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;


@Controller
public class TaskController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    public TaskService taskService;

    // posting to different thing without actually being a html thing users can access
    @PostMapping("/tasks")
    public String handleTaskSubmission(@RequestParam String title,@RequestParam String description, @RequestParam String priority, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate duedate, HttpSession session, RedirectAttributes redirectAttributes) {
        String email = (String) session.getAttribute("email");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

    	// Save task to database 
        Task task = new Task(user, title, description, duedate); //im pretty sure the due date time is autodone? and idk about id
        taskService.addTask(user,task);

        // Redirect back to dashboard
        redirectAttributes.addFlashAttribute("message", "Task added successfully!");
        String customURL = "/" + user.getFirstName().toLowerCase() + "_" + user.getLastName().toLowerCase() + "/dashboard";
        return "redirect:" + customURL;
    }
}
