package com.example.bpm.dto;

import com.example.bpm.entity.ProjectEntity;
import com.example.bpm.entity.ProjectRoleEntity;
import com.example.bpm.entity.RoleEntity;
import com.example.bpm.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRoleDto {
    private ProjectEntity projectIdToRole;
    private UserEntity uuid;
    private RoleEntity role;

    public static ProjectRoleDto toProjectRoleDto(ProjectRoleEntity projectRoleEntity){
        ProjectRoleDto projectRoleDto = new ProjectRoleDto();
        projectRoleDto.setProjectIdToRole(projectRoleEntity.getProjectIdToRole());
        projectRoleDto.setUuid(projectRoleEntity.getUuid());
        projectRoleDto.setRole(projectRoleEntity.getRole());
        return projectRoleDto;
    }
}
