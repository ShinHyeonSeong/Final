package com.example.bpm.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "project")
public class ProjectEntity {
    @Id
    @Column(name = "projectId")
    private Long projectId;

    @Column(name = "title")
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "projectPublic")
    private Long projectPublic;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectId")
    private List<ProjectRoleEntity> projectRoleEntityList1 = new ArrayList<>();


}
