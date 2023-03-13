package com.example.bpm.dto;

import com.example.bpm.entity.FriendEntity;
import com.example.bpm.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//DB에는 타입이 Long이나 String 인데 여기서는 객체형식으로 반환 (서비스 클래스에서 한번 수정하는 방식이 필요해 보임
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendDto {
private UserEntity user1;
private UserEntity user2;

    public static FriendDto toFriendDto(FriendEntity friendEntity) {
        FriendDto friendDto = new FriendDto();
        friendDto.setUser1(friendEntity.getUser1());
        friendDto.setUser2(friendEntity.getUser2());
        return friendDto;
    }

}