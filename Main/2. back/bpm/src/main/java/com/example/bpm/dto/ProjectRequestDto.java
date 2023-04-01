package com.example.bpm.dto;

import com.example.bpm.entity.ProjectRequestEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequestDto {
    private String sendUUID;
    private String recvUUID;

    public static ProjectRequestDto toProjectRequestDto(ProjectRequestEntity projectRequestEntity) {
        ProjectRequestDto projectRequestDto = new ProjectRequestDto();
        projectRequestDto.setSendUUID(String.valueOf(projectRequestEntity.getSendUUID()));
        projectRequestDto.setRecvUUID(String.valueOf(projectRequestEntity.getRecvUUID()));
        return projectRequestDto;
    }
}
