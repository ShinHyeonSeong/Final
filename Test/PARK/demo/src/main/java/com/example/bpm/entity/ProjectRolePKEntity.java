package com.example.bpm.entity;

import jakarta.persistence.Column;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class ProjectRolePKEntity implements Serializable {
    @Column(name = "project_id")
    private Long projectIdInRole;
    @Column(name = "uuid")
    private String uuidInRole;

}
