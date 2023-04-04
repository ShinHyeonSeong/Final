package com.example.bpm.entity;

import com.example.bpm.dto.ProjectRequestDto;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "projectRequst")
@IdClass(ProjectRequestPKEntity.class)
public class ProjectRequestEntity {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sendUUID")
    private UserEntity sendUUID;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recvUUID")
    private UserEntity recvUUID;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectID")
    private ProjectEntity prjoectIdToRequest;


    public static ProjectRequestEntity toProjectRequestEntity(ProjectRequestDto projectRequestDto) {
        ProjectRequestEntity projectRequestEntity = new ProjectRequestEntity();
        projectRequestEntity.setSendUUID(projectRequestDto.getSendUUID());
        projectRequestEntity.setRecvUUID(projectRequestDto.getRecvUUID());
        return projectRequestEntity;
    }
}
