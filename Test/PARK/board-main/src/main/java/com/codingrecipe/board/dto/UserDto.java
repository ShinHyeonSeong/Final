package com.codingrecipe.board.dto;

import com.codingrecipe.board.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String uuid;

    //회원가입을 할 때 필요한 생성자
    public UserDto(String email, String password, String name) {
        this.uuid = UUID.randomUUID().toString();
    }


    public static UserDto toUserDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setUuid(userEntity.getUuid());
        return userDto;
    }
}
