/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.ToDoListApp.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tasks")
public class Task {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false) // user_id refers to users.id
    private User user;

    @Column(nullable=false)
    private String title;

    @Column()
    private String description;
   
    //i dont know if im gonna mess something up so im just gonna write stuff in comments for now.
    //private int deadline_year;
    //private int deadline_month;
    //private int deadline_date;


    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;


    // Constructor (no argument)
    public Task() {}

    // Constructor (w/o user, w/o descrition)
    public Task(String title) {
        this.title = title;
    }

    // Constructor (w/o user)
    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // Constructor (with user, w/o description)
    public Task(User user, String title) {
    	this.user = user;
        this.title = title;

    }

    // Constructor (with user)
    public Task(User user, String title, String description) {
        this.user = user;
        this.title = title;
        this.description = description;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
