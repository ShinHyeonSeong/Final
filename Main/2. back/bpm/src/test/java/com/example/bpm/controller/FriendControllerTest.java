package com.example.bpm.controller;

import com.example.bpm.service.ProjectService;
import com.example.bpm.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(FriendController.class)
//@AutoConfigureWebMvc 이 어노테이션을 통해 MockMvc를 Builder 없이 주입 받을 수 있음
class FriendControllerTest {

    @MockBean
    UserService userService;
    @MockBean
    ProjectService projectService;

    @Test
    @DisplayName("친구 리스트 가져오기")//콘솔창에 메서드를 표현해주는 식별 문자열
    void friendList() {

    }

    @Test
    void gofriendPlus() {
    }

    @Test
    void friendSearch() {
    }
}