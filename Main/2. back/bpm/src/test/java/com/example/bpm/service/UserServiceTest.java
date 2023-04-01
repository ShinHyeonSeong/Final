package com.example.bpm.service;

import com.example.bpm.dto.UserDto;
import com.example.bpm.entity.UserEntity;
import com.example.bpm.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Test
    void save() {
        //given
        UserDto userDto = new UserDto("email", "1234", "name");

        //when
        UserEntity userEntity = UserEntity.toUserEntity(userDto);
        userRepository.save(userEntity);

        //then
        assertEquals(userEntity, userRepository.findById(userEntity.getUuid()));
    }
}