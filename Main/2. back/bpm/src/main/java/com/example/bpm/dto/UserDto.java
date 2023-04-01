package com.example.bpm.dto;

import com.example.bpm.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto {
    private String uuid;
    private String email;
    private String password;
    private String name;

    //UserDto는 입력 받는 값은 세개 밖에 없고 받는 순간 바로 uuid 자동 생성 근데 시발 이거 테스트 코드는 됬는데 잘 몰겠다
    public UserDto(String email, String password, String name) {
        this.uuid = UUID.randomUUID().toString();
        this.email = email;
        this.password = password;
        this.name = name;
    }

//static 푸는 순간 다 죽는거여!!! 이거 바뀌면 객체 바뀐다
    public static UserDto toUserDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setUuid(userEntity.getUuid());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPassword(userEntity.getPassword());
        userDto.setName(userEntity.getName());
        return userDto;
    }

}
