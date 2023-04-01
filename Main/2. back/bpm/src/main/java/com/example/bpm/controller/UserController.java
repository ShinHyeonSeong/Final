package com.example.bpm.controller;

import com.example.bpm.dto.UserDto;
import com.example.bpm.repository.UserRepository;
import com.example.bpm.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
//이 어노테이션 오류시 RequiedArgConstructor 변환 필요 -> 이거 걍 자동 생성자를 만들어주고 우선순위 설정해주는 어노테이션인데 걍 써볼라고
@Builder
public class UserController {

    //불변성을 얻게 되어 실행 중 객체가 변하는 것을 막을 수 있고 이로 인해 오류를 방지할 수 있다.
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/user/save")
    public String goSave() {
        return "user/save";
    }

    @PostMapping("/user/save")
    public String doSave(@ModelAttribute UserDto userDto) {
        userService.save(userDto);
        log.info("회원가입 정상 작동 (컨트롤러 작동)");
        return "user/login";
    }

    @GetMapping("/user/login")
    public String login() {
        return "user/login";
    }

    //이 세션 처리 예제 내가 template에 만들어 둠
    @PostMapping("/user/login")
    public String login(@ModelAttribute UserDto userDto, HttpSession session) {
        UserDto loginResult = userService.login(userDto);
        if (loginResult != null) {
            //세션에 로그인한 정보롤 담아줌 -> main 창에 적용되고 main에 이 세션을 이용할 수 있는 thyleaf가 적용되는 것이다.
            session.setAttribute("loginInfo", loginResult);
            //로그인 성공 알림창 만들어줘야함
            log.info("로그인 성공 (컨트롤러 작동)");
            return "user/main";
        } else {
            //로그인 실패 알림창을 만들어줘야함
            log.info("로그인 실패 (컨트롤러 작동)");
            return "user/login";
        }
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        //세션으로 로그아웃 처리
        session.invalidate();
        log.info("로그아웃 성공 (컨트롤러)");
        return "user/index";
    }

    //프로필로 가는 메서드
    @GetMapping("/user/{id}")
    public String findById(@PathVariable String id, Model model) {
        UserDto userDto = userService.findById(id);
        model.addAttribute("user", userDto);
        log.info("회원정보 찾기 성공 (컨트롤러) detail 페이지로 이동");
        return "user/detail";
    }


    //회원 정보 변경을 위한 회원 정보가 담긴 페이지 열기
    @GetMapping("/user/update")
    public String updateForm(HttpSession session, Model model) {
        String myId = (String) session.getAttribute("loginInfo.id");
        UserDto userDto = userService.updateForm(myId);
        model.addAttribute("updateUser", userDto);
        return "user/update";
    }

    //회원 정보 변경 시 메서드
    @PostMapping("/user/update")
    public String update(@ModelAttribute UserDto userDto) {
        userService.update(userDto);
        log.info("정상 업데이트 되었습니다 (컨트롤러 작동)");
        return "redirect:/user/" + userDto.getUuid();
    }


    @GetMapping("/user/delete/{id}")
    public String deleteById(@PathVariable String id) {
        userService.deleteById(id);
        log.info("탈퇴되었습니다 (컨트롤러 작동)");
        return "redirect:/index";
    }

//    여기까지가 User 단순 CRUD

// 프로젝트 초대 기능
    @GetMapping("/user/invite")
    public
}
