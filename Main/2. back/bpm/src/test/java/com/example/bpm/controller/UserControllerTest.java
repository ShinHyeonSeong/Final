//package com.example.bpm.controller;
//
//import com.example.bpm.dto.UserDto;
//import com.example.bpm.entity.UserEntity;
//import com.example.bpm.service.UserService;
//import jakarta.transaction.Transactional;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class UserControllerTest {
//
//    @Autowired UserService userService;
//
//    @Test
//    @DisplayName("회원가입 테스트")
//    void doSave() {
//        //given
//        UserDto userDto = new UserDto("email", "1234", "name");
//        UserEntity userEntity = userService.save(userDto);
//        //when
//        UserDto userDto1 = new UserDto("email", "1234", "name");
//        UserEntity userEntity1 = userService.save(userDto1);
//        //then
//        assertEquals(userEntity, userEntity1);
//    }
//}