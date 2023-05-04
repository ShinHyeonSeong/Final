package com.example.bpm.dto;

import com.example.bpm.entity.ProjectEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long projectId;
    private String title;
    private String subtitle;
    private Long projectPublic;

    public static ProjectDto toProjectDto(ProjectEntity projectEntity) {
        ProjectDto projectDto = new ProjectDto(projectEntity.getProjectId(), projectEntity.getTitle(), projectEntity.getSubtitle(), projectEntity.getProjectPublic());
        return projectDto;
    }
}
