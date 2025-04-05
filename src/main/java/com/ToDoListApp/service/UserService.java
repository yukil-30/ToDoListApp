/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.ToDoListApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ToDoListApp.entity.User;
import com.ToDoListApp.repository.UserRepository;

/**
 *
 * @author yukili
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // public void createUser() {
    //     User user1 = new User("John", "Doe", "johndoe@gmail.com", passwordEncoder.encode("1234"));
    //     userRepository.save(user1);
    //     System.out.println(user1.toString());
    // }

    public User registerUser(String firstName, String lastName, String email, String password) {
        User user = new User(firstName, lastName, email, passwordEncoder.encode(password));
        System.out.println(user.toString());
        return userRepository.save(user);
    }

}
