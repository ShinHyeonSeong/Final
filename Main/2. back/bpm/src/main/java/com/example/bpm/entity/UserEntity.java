package com.example.bpm.entity;

import com.example.bpm.dto.UserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.catalina.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "uuid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uuid;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;

    //주인으로 설정된 부분은 DB 의 연관관계와 매핑되서 외래키를 관리하고, 다른 한쪽은 읽기만 가능하게된다.
//외래키를 관리하는쪽 즉, 주인은 @JoinColumn 을 써주고, 그렇지 않은쪽은 mappedBy 를 쓰는것이다.
//(단방향일때는 mappedBy 안써도됨)
//@JoinColumn 어노테이션을 이용해서, 외래키가 어디에 있는지 알려준다는점이 중요하다.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user1")
    private List<FriendEntity> friendEntityList1 = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user2")
    private List<FriendEntity> friendEntityList2 = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sendUUID")
    private List<FriendRequestEntity> friendRequestEntityList1 = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recvUUID")
    private List<FriendRequestEntity> friendRequestEntityList2 = new ArrayList<>();
    //Entity -> DTO 변환 메서드
    public static UserEntity toUserEntity(UserDto userDto) {

        UserEntity userEntity = new UserEntity();
        userEntity.setUuid(userDto.getUuid());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setEmail((userDto.getEmail()));
        return userEntity;
    }

    //새로운 정보의 DTO를 받아 Entity를 최신화 (update) 시키는 메서드
    public static UserEntity toUpdateuserEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUuid(userDto.getUuid());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setName(userDto.getName());
        return userEntity;
    }
}