package com.taskmanager.application;

import com.taskmanager.domain.User;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    @Override
    public User registerUser(String username, String email, String role) {
        return null;
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.empty();
    }
}
