package com.ToDoListApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import com.ToDoListApp.entity.Task;
import com.ToDoListApp.entity.User;
import com.ToDoListApp.repository.TaskRepository;
import com.ToDoListApp.repository.UserRepository;
import com.ToDoListApp.service.TaskService;
import com.ToDoListApp.service.UserService;

import java.time.LocalDate;
import java.util.UUID;

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
    @Transactional
    public String handleTaskSubmission(@RequestParam String title,@RequestParam String description, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate duedate, HttpSession session, RedirectAttributes redirectAttributes) {
        String email = (String) session.getAttribute("email");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

    	//This saves the task to database by creating it
        Task task = new Task(user, title, description, duedate); //im pretty sure the created/modify time is autodone? and idk about id
        user.addTask(task);
        taskRepository.save(task);
        //userRepository.save(user); //cause of cascading we can save the user again
        
        // Redirect back to dashboard
        redirectAttributes.addFlashAttribute("message", "Task added successfully!");
        return MakeDashboardURL(user);
    }
    
    
    @Transactional
    @PostMapping("/delete_all_tasks")
    public String deleteAllTasks(HttpSession session, RedirectAttributes redirectAttributes){
        String email = (String) session.getAttribute("email");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.getTasks().clear();
        userRepository.save(user);
        //taskRepository.deleteByUser(user); //clearing the user.gettasks.clear would be slower so instead do this
        redirectAttributes.addFlashAttribute("message", "All Tasks Deleted Successfully!");
        return MakeDashboardURL(user);
    }
    
    @Transactional
    @PostMapping("/delete/task/{id}")
    public String deleteOneTask(@PathVariable UUID id, HttpSession session, RedirectAttributes redirectAttributes){
        String email = (String) session.getAttribute("email");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        int taskSize = user.getTasks().size();
        String name = "";
        for(int i = 0; i < taskSize; i++) {
        	if(user.getTasks().get(i).getId().equals(id)) {
        		name = user.getTasks().get(i).getTitle();
        		user.getTasks().remove(i);
        		break;
        	}
        }
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("message", "The Task " + name + " was Deleted Successfully!");
        return MakeDashboardURL(user);
    }

    
    @GetMapping("/modify/task/{id}")
	public String updateModifiedtask(@PathVariable UUID id, HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        int taskSize = user.getTasks().size();
        for(int i = 0; i < taskSize; i++) {
        	if(user.getTasks().get(i).getId().equals(id)) {
        		model.addAttribute("task", user.getTasks().get(i));
        		break;
        	}
        }

		return "modify";
	}



    @Transactional
    @PostMapping("/modify/task/{id}")
	public String modifyTasks(@PathVariable UUID id, @RequestParam String title,@RequestParam String description, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate duedate, HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        int taskSize = user.getTasks().size();
        Task task = null;
        for(int i = 0; i < taskSize; i++) {
        	if(user.getTasks().get(i).getId().equals(id)) { //always happens
        		task = user.getTasks().get(i);
        		break;
        	}
        }
        task.setTitle(title);
        task.setDueDate(duedate);
        task.setDescription(description);
        
        taskRepository.save(task);
		return MakeDashboardURL(user);
	}

    private String MakeDashboardURL(User user) {
        String customURL = "/" + user.getFirstName().toLowerCase() + "_" + user.getLastName().toLowerCase() + "/dashboard";
    	return "redirect:" + customURL;
    }

}
