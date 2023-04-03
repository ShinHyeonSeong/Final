package com.example.bpm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Column
    private String title;
    @Column
    private String subtitle;
    @Column
    private Long projectPublic;


}
