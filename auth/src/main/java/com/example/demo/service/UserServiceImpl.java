package com.example.demo.service;

import com.example.demo.model.User;

import java.util.Optional;

public interface UserServiceImpl {
    public Optional<User> selectUserByUsername(String username);
}
