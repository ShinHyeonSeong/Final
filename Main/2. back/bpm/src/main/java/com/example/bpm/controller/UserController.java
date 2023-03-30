package com.example.bpm.controller;

import com.example.bpm.dto.UserDto;
import com.example.bpm.repository.UserRepository;
import com.example.bpm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor//생성자 주입 어노테이션 @Autorized와 비슷하게 생성자로 객체를 만들지 않아도 자동 주입 가능하게 함
public class UserController {

    //불변성을 얻게 되어 실행 중 객체가 변하는 것을 막을 수 있고 이로 인해 오류를 방지할 수 있다.
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/user/save")
    public String goSave() {
        return "user/save";
    }

    @PostMapping("/user/save")
    public String doSave(@ModelAttribute UserDto userDto){
        userService.save(userDto);
        log.info("회원가입 정상 작동");
        return "user/login";
    }

}
