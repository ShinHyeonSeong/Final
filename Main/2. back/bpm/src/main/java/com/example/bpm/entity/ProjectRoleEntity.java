package com.example.bpm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "projectRole")
@IdClass(ProjectRolePKEntity.class)
public class ProjectRoleEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId")
    private ProjectEntity projectIdToRole;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid")
    private UserEntity uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role")
    private RoleEntity role;



}
