package com.example.bpm.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequestPKEntity implements Serializable {
    @Column(name = "send_uuid")
    private UserEntity sendUUID;
    @Column(name = "recv_uuid")
    private UserEntity recvUUID;
}
