package com.example.bpm.entity;

import jakarta.persistence.*;
import lombok.Data;
//확인사항
// NOT NULL이 있는데 RequiredArgs 어노테이션 없어 Service에 적용 가능한가??
@Data
@Entity
@Table(name = "friend")
@IdClass(FriendPKEntity.class)
public class FriendEntity {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_uuid" )
    private UserEntity user1;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_uuid" )
    private UserEntity user2;

}