package com.example.assignment_buddy_user_service.service;


import com.example.assignment_buddy_user_service.model.User;

public interface UserService {

    User register(User user);

    User login(String email, String password);
}
