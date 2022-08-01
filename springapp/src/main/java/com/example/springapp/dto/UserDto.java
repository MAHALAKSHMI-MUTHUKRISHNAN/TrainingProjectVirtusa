package com.example.springapp.dto;

import lombok.Data;

@Data
public class UserDto {
    private long id;
    private String role;
    private String name;
    private String username;
    private String mobile;
    private String email;
    private String password;
}
