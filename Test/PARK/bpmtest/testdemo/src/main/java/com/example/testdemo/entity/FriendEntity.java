package com.example.testdemo.entity;

import com.example.testdemo.dto.FriendDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "friend")
@IdClass(FriendPK.class)
public class FriendEntity {
    @Id
    @GeneratedValue(strategy    = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id1")
    private AccountEntity id1;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id2")
    private AccountEntity id2;

    public static FriendEntity toFriendEntity(FriendDto friendDto) {
        FriendEntity friendEntity = new FriendEntity();
        friendEntity.setId(friendDto.getId());
        friendEntity.setId1(friendDto.getId1());
        friendEntity.setId2((friendDto.getId2()));
        return friendEntity;
    }

}