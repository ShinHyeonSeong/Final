package com.example.bpm.dto;

import com.example.bpm.entity.ProjectRequestEntity;
import com.example.bpm.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequestDto {
    private UserEntity sendUUID;
    private UserEntity recvUUID;

    public static ProjectRequestDto toFriendRequestDto(ProjectRequestEntity projectRequestEntity) {
        ProjectRequestDto friendRequestDto = new ProjectRequestDto();
        friendRequestDto.setSendUUID(projectRequestEntity.getSendUUID());
        friendRequestDto.setRecvUUID(projectRequestEntity.getRecvUUID());
        return friendRequestDto;
    }
}