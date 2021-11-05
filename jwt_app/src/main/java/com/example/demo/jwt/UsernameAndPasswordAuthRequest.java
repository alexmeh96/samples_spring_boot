package com.example.demo.jwt;

import lombok.Data;

@Data
public class UsernameAndPasswordAuthRequest {

    private String username;
    private String password;
}
