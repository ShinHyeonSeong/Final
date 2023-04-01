package com.example.bpm.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "role")
public class RoleEntity {
    @Id
    private Long roleId;

    @Column
    private String roleName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private List<ProjectRoleEntity> projectRoleEntityList = new ArrayList<>();
}
