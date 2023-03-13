package com.example.bpm.dto;

import com.example.bpm.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestEntity {
    private UserEntity sendUUID;
    private UserEntity recvUUID;
}