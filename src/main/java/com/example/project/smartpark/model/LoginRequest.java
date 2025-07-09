package com.example.project.smartpark.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
