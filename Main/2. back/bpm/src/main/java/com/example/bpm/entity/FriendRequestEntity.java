package com.example.bpm.entity;

import com.example.bpm.entity.FriendRequestPKEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "friendRequst")
@IdClass(FriendRequestPKEntity.class)
public class FriendRequestEntity {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sendUUID_uuid")
    private UserEntity sendUUID;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recvUUID_uuid")
    private UserEntity recvUUID;
}