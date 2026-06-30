package com.taskmanager.application;

import com.taskmanager.domain.User;

import java.util.Optional;

public interface UserService {

    User registerUser(String username, String email, String role);
    
    Optional<User> getUserById(Long userId);
    
    Optional<User> getUserByEmail(String email);
}
