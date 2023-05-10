package com.example.bpm.dto;

import com.example.bpm.entity.ProjectEntity;
import com.example.bpm.repository.ProjectRepository;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long projectId;
    private String title;
    private String subtitle;

    public static ProjectDto toProjectDto(ProjectEntity projectEntity) {
        ProjectDto projectDto = new ProjectDto(projectEntity.getProjectId(), projectEntity.getTitle(), projectEntity.getSubtitle());
        return projectDto;
    }
}
