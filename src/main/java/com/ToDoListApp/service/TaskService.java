package com.ToDoListApp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ToDoListApp.entity.Task;
import com.ToDoListApp.entity.User;
import com.ToDoListApp.repository.TaskRepository;
import com.ToDoListApp.repository.UserRepository;

@Service
public class TaskService {

    @Autowired
    private final TaskRepository taskRepository;

    @Autowired
    private final UserRepository userRepository;

    public TaskService (TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;

    }

    // Create a task that has 
    public Task createTask(String title, String description, LocalDate dueDate){

        Task newTask = new Task(title, description, dueDate);

        return newTask;
    }

    // Add Task to given User
    public Task addTask(User user, Task task) {
        
        // Assign the task to the user
        task.setUser(user);

        // Save the task (JPA will automatically update the user's tasks list)
        return taskRepository.save(task);
    }

    // Add Task to given user ID
    public Task addTask(UUID id, Task task) {

        User user;
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        else{
            throw new IllegalArgumentException("User not found");
        }
        
        // Assign the task to the user
        task.setUser(user);

        // Save the task (JPA will automatically update the user's tasks list)
        return taskRepository.save(task);
    }

    // Get tasks list for given specific user id
    public List<Task> getTasksForUser(UUID id) {
        
        return taskRepository.findByUserId(id);
    }

}