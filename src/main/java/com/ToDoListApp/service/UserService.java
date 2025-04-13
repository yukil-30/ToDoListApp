/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.ToDoListApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ToDoListApp.entity.User;
import com.ToDoListApp.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    // New User and store to database
    public User createUser(String firstName, String lastName, String email, String password) {

        User newUser = new User(firstName, lastName, email, password);

        // Save new user information to database
        return userRepository.save(newUser);
    }


}
