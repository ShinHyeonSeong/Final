package com.example.bpm.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectRequestPKEntity implements Serializable {
    private UserEntity sendUUID;
    private UserEntity recvUUID;
}
