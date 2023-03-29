package com.example.bpm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "friendRequst")
@IdClass(ProjectRequestPKEntity.class)
public class ProjectRequestEntity {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sendUUID_uuid")
    private UserEntity sendUUID;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recvUUID_uuid")
    private UserEntity recvUUID;
}