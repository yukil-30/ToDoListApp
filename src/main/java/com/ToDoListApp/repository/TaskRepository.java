package com.ToDoListApp.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ToDoListApp.entity.Task;
import com.ToDoListApp.entity.User;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    // Find Tasks by user ID
    List<Task> findByUserId(UUID id);

    // Find Tasks by user entity
    List<Task> findByUser(User user);
    

}
