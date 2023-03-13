package com.example.testdemo.dto;

import com.example.testdemo.entity.AccountEntity;
import com.example.testdemo.entity.FriendEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendDto {
    private Long id;
    private AccountEntity id1;
    private AccountEntity id2;

    public static FriendDto toFriendDto(FriendEntity friendEntity) {
        FriendDto friendDto = new FriendDto();
        friendDto.setId(friendEntity.getId());
        friendDto.setId1(friendDto.getId1());
        friendDto.setId2(friendDto.getId2());
        return friendDto;
    }
}