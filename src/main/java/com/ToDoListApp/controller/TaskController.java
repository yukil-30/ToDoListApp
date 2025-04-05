/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/package-info.java to edit this template
 */

package com.ToDoListApp.controller;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ToDoListApp.entity.Task;
import com.ToDoListApp.entity.User;
import com.ToDoListApp.repository.TaskRepository;
import com.ToDoListApp.repository.UserRepository;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public Task addTask(@RequestParam UUID userId, @RequestParam String title, @RequestParam String description) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Task task = new Task();
        task.setUser(user.get());
        task.setTitle(title);
        task.setDescription(description);

        return taskRepository.save(task);
    }

    @GetMapping("/user/{userId}")
    public List<Task> getTasks(@PathVariable UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(taskRepository::findByUser).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PutMapping("/update/{taskId}")
    public Task updateTaskStatus(@PathVariable UUID taskId, @RequestParam String status) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        return taskRepository.save(task);
    }
}
