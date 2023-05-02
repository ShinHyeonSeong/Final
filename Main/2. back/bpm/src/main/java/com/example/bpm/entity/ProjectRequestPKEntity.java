package com.example.bpm.entity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequestPKEntity implements Serializable {
    @Column(name = "send_uuid")
    private UserEntity sendUUID;
    @Column(name = "recv_uuid")
    private UserEntity recvUUID;
}
