package com.example.DTOs;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserOutDTO {

    private long userId;
    private String uuid;
    private String name;
    private String description;
    private Timestamp modifiedAt;
}