package com.example.bpm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequestPKEntity implements Serializable {
    private UserEntity sendUUID;
    private UserEntity recvUUID;
    private ProjectEntity prjoectIdToRequest;
}
