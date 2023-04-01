package com.example.bpm.controller;

import com.example.bpm.dto.UserDto;
import com.example.bpm.repository.UserRepository;
import com.example.bpm.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserControllerTest {

    @Autowired
    UserService userService;
    UserRepository userRepository;

//    차례대로 테스트 생명주기 이다.
//    @BeforeAll //전체 테스트 시작 전에 실행되어야 되는 어노테이션
//    @BeforeEach // 하나의 테스트를 실행하기전에 시작되는 어노테이션
//    @Test //테스트 코드
//    @AfterEach //하나의 테스트가 끝나면 실행되는 어노테이션
//    @AfterAll //전체 테스트가 끝나면 실행되는 어노테이션

    @Test
    @DisplayName("회원가입 테스트")
        //콘솔창에 메서드 명을 선언해주는 어노테이션
    void save() {
        //given
            UserDto userDto = new UserDto("email", "1234", "name");
        //when
            UserDto userDto1 = new UserDto("email", "1234", "name");

        //then
        assertEquals(userDto,userDto1);
    }

    @Test
    @Disabled
//테스트를 실행하지 않겠다는 어노테이션
    void login() {

    }

}