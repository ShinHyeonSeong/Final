package com.example.bpm.dto;

import com.example.bpm.entity.UserEntity;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private UUID uuid;
    private String email;
    private String password;
    private String name;

    public static UserDto toUserDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setUuid(UUID.fromString(String.valueOf(userEntity.getUuid())));
        userDto.setEmail(userEntity.getEmail());
        userDto.setPassword(userEntity.getPassword());
        userDto.setName(userEntity.getName());
        return userDto;
    }
}