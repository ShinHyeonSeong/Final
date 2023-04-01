package com.example.bpm.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectRolePKEntity implements Serializable {

    private ProjectEntity projectId;

    private UserEntity uuid;

}
