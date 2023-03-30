package com.example.bpm.service;

import com.example.bpm.dto.UserDto;
import com.example.bpm.entity.UserEntity;
import com.example.bpm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor//생성자 주입 어노테이션 @Autorized와 비슷하게 생성자로 객체를 만들지 않아도 자동 주입 가능하게 함
public class UserService {
    final private UserRepository userRepository;

    //회원가입
    public UserEntity save(UserDto userDto) {
        UserEntity userEntity = UserEntity.toUserEntity(userDto);
        userRepository.save(userEntity);
        return userEntity;
    }

}
