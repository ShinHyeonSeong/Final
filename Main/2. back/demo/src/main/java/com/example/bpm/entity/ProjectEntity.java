package com.example.bpm.entity;

import com.example.bpm.dto.ProjectDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project")
@Slf4j
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "title")
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectIdInRole", cascade = CascadeType.REMOVE)
    private List<ProjectRoleEntity> projectRoleEntityList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectIdToRequest")
    private List<ProjectRequestEntity> projectRequestEntityList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectIdToHead")
    private List<HeadEntity> HeadEntityList = new ArrayList<>();

    public static ProjectEntity toProjectEntity(ProjectDto projectDto) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectId(projectDto.getProjectId());
        projectEntity.setTitle(projectDto.getTitle());
        projectEntity.setSubtitle(projectDto.getSubtitle());
        return projectEntity;
    }

}
