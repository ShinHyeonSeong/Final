package com.example.bpm.entity;

import com.example.bpm.dto.ProjectDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "project")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private int projectId;

    @Column(name = "title")
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "project_public")
    private String projectPublic;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectIdInRole")
    private List<ProjectRoleEntity> projectRoleEntityList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectId")
    private List<ProjectRequestEntity> projectRequestEntityList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectIdToHead")
    private List<HeadEntity> HeadEntityList = new ArrayList<>();

    public static ProjectEntity toProjectEntity(ProjectDto projectDto) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectId(projectDto.getProjectId());
        projectEntity.setTitle(projectDto.getTitle());
        projectEntity.setSubtitle(projectDto.getSubtitle());
        projectEntity.setProjectPublic(projectDto.getProjectPublic());
        return projectEntity;
    }

}
