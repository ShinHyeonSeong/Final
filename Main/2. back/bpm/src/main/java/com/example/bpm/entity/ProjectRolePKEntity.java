package com.example.bpm.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class ProjectRolePKEntity implements Serializable {
    @Column(name = "project_id")
    private Long projectIdInRole;
    @Column(name = "uuid")
    private String uuidInRole;

}
