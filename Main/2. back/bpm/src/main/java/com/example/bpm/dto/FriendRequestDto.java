package com.example.bpm.dto;

import com.example.bpm.entity.FriendRequestEntity;
import com.example.bpm.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestDto {
    private UserEntity sendUUID;
    private UserEntity recvUUID;

    public static FriendRequestDto toFriendRequestDto(FriendRequestEntity friendRequestEntity) {
        FriendRequestDto friendRequestDto = new FriendRequestDto();
        friendRequestDto.setSendUUID(friendRequestEntity.getSendUUID());
        friendRequestDto.setRecvUUID(friendRequestEntity.getRecvUUID());
        return friendRequestDto;
    }
}