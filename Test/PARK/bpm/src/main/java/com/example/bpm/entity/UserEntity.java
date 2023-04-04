package com.example.bpm.entity;

import com.example.bpm.dto.UserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;

    //외래키 설정
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sendUUID")
    private List<ProjectRequestEntity> projectRequestEntityList1 = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recvUUID")
    private List<ProjectRequestEntity> projectRequestEntityList2 = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "")

    //Entity -> DTO 변환 메서드
    public static UserEntity toUserEntity(UserDto userDto) {

        UserEntity userEntity = new UserEntity();
        userEntity.setUuid(String.valueOf(userDto.getUuid()));
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setEmail((userDto.getEmail()));
        return userEntity;

    }
    //새로운 정보의 DTO를 받아 Entity를 최신화 (update) 시키는 메서드
    public static UserEntity toUpdateuserEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUuid(String.valueOf(userDto.getUuid()));
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setName(userDto.getName());
        return userEntity;
    }
}
