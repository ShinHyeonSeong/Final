package com.example.bpm.dto;

import com.example.bpm.entity.ProjectEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private int projectId;
    private String title;
    private String subtitle;
    private String projectPublic;

    public static ProjectDto toProjectDto(ProjectEntity projectEntity) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setProjectId(projectEntity.getProjectId());
        projectDto.setTitle(projectEntity.getTitle());
        projectDto.setSubtitle(projectEntity.getSubtitle());
        projectDto.setProjectPublic(projectEntity.getProjectPublic());
        return projectDto;
    }
}
