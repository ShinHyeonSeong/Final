package com.example.bpm.entity;

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
}
