package com.example.bpm.controller;

import com.example.bpm.dto.UserDto;
import com.example.bpm.repository.UserRepository;
import com.example.bpm.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
@Slf4j
@RequiredArgsConstructor //생성자 주입 어노테이션 @Autorized와 비슷하게 생성자로 객체를 만들지 않아도 자동 주입 가능하게 함
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/user/save")
//a 태그로 불러온 주소값
    public String saveForm() {
        return "User/save";
    }
//
//    @PostMapping("/member/save")
//    public String save(@RequestParam("memberEmail") String memberEmail,
//                       @RequestParam("memberPassword") String memberPassword,
//                       @RequestParam("memberName") String memberName) {
//        return "index";
//    }

    @PostMapping("/user/save")
    //매개변수 @ModelAttribute 속성은 html 파일의 form 태그 안 name 속성들의 값들을 dto에 맞게 받아올 수 있다.
    //form 결과로 보여지는 창
    public String save(@ModelAttribute UserDto userDto) {
        System.out.println("UserController.save");
        System.out.println("userDTO = " + userDto);
        userService.save(userDto);
        return "User/login";
    }

    @GetMapping("/user/login")
    public String login() {
        return "User/login";
    }

    @PostMapping("/user/login")
    public String login(@ModelAttribute UserDto userDto, HttpSession session) {
        UserDto loginResult = userService.login(userDto);
        if (loginResult != null) {
            //세션에 로그인한 정보롤 담아줌
//            session.setAttribute("loginEmail", loginResult.getUserEmail());
            return "User/main";
        } else {
            return "User/login";
        }
    }

    @GetMapping("/user/")
    //사용자 목록 보여주기
    public String findAll(Model model) {
        List<UserDto> userDtoList = userService.findAll();
        model.addAttribute("userList", userDtoList);
        return "User/list";
    }

    @GetMapping("/user/{id}")
    public String findById(@PathVariable Long id, Model model) {
        UserDto userDto = userService.findById(id);
        model.addAttribute("user", userDto);
        return "User/detail";
    }

    @GetMapping("/user/update")
    public String updateForm(HttpSession session, Model model) {
        String myEmail = (String) session.getAttribute("loginEmail");
        log.info("친구 리스트 업데이트");
        UserDto userDto = userService.updateForm(myEmail);
        model.addAttribute("updateUser", userDto);
        return "User/update";
    }

    @PostMapping("/user/update")
    public String update(@ModelAttribute UserDto userDto) {
        userService.update(userDto);
        return "redirect:/user/" + userDto.getUuid();
    }

    @GetMapping("/user/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/user/";
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        //세션으로 로그아웃 처리
        session.invalidate();
        return "User/index";
    }

}