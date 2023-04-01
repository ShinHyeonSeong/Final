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

//Dto는 String 형식으로 받아야 하고 (get)
//Entity는 Entity 객체 형식으로 받아야 함(set)
    public UserEntity getSendUUID() {
        return this.getuuid;
    }

    public void setSendUUID(UserEntity sendUUID) {
        this.sendUUID = sendUUID;
    }

    public UserEntity getRecvUUID() {
        return recvUUID;
    }

    public void setRecvUUID(UserEntity recvUUID) {
        this.recvUUID = recvUUID;
    }

    public static ProjectRequestEntity toProjectRequestEntity(ProjectRequestDto sendUUID, ProjectRequestDto recvUUID) {
        ProjectRequestEntity projectRequestEntity = new ProjectRequestEntity();
        projectRequestEntity.setSendUUID(sendUUID);
        projectRequestEntity.setRecvUUID(recvUUID);

    }
}
