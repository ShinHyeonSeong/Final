package com.example.bpm.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserPKEntity implements Serializable {
    private String uuid;
    private String email;
}
