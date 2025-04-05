/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.ToDoListApp.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.ToDoListApp.entity.Task;
import com.ToDoListApp.entity.User;




public interface TaskRepository extends CrudRepository<Task, UUID>{
    List<Task> findByUser(User user);
}
