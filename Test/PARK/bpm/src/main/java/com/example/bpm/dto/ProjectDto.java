package com.example.bpm.dto;

import com.example.bpm.entity.ProjectEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long projectid;
    private String title;
    private String subtitle;
    private Long projectPublic;

    public ProjectDto toProjectDto(ProjectEntity projectEntity) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setProjectid(projectEntity.getProjectId());
        projectDto.setTitle(projectEntity.getTitle());
        projectDto.setSubtitle(projectEntity.getSubtitle());
        projectDto.setProjectPublic(projectEntity.getProjectPublic());
        return projectDto;
    }
}
