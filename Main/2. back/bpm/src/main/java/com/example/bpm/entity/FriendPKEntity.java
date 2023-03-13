package com.example.bpm.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class FriendPKEntity implements Serializable {
    private UserEntity user1;
    private UserEntity user2;
}