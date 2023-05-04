package com.example.bpm.entity;

import com.example.bpm.dto.RoleDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "role")
public class RoleEntity {
    @Id
    private Long Id;

    @Column
    private String roleName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private List<ProjectRoleEntity> projectRoleEntityList = new ArrayList<>();

    public static RoleEntity toRoleEntity(RoleDto roleDto) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(roleDto.getId());
        roleEntity.setRoleName(roleDto.getRoleName());
        return roleEntity;
    }
}