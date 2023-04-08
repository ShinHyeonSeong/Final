package com.example.bpm.controller;

import com.example.bpm.dto.UserDto;
import com.example.bpm.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@ToString
@RequiredArgsConstructor
public class UserController {

    final private UserService userService;

    @GetMapping("/user/gosave")
    public String goSave() {
        return "user/save";
    }


    @PostMapping("/user/dosave")
    public String save(@RequestParam("email") String email,
                       @RequestParam("password") String password,
                       @RequestParam("username") String name) {
        //UUID 부여를 위해 생성자로 접근을 한번 더한다
        UserDto NewUser = new UserDto(email, password, name);
        log.info("DTO 정상 값 입력 (컨트롤러)" + "/" + email + "/" + password + "/" + name);
        UserDto result = userService.save(NewUser);
        if (result == null) {
            log.info("서비스에서 NULL값이 넘어옴 (컨트롤러 작동");
            return null;
        } else {
            log.info("save 정상 작동 (컨트롤러)");
            return "user/login";
        }
    }


    @GetMapping("/user/gologin")
    public String login() {
        return "user/login";
    }


    @PostMapping("/user/dologin")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpSession session, Model model) {
        UserDto loginResult = userService.login(email, password);
        if (loginResult != null) {
            //세션에 로그인한 정보롤 담아줌 -> main 창에 적용되고 main에 이 세션을 이용할 수 있는 thyleaf가 적용되는 것이다.
            session.setAttribute("userInfo", loginResult);
            //로그인 성공 알림창 만들어줘야함
            log.info("로그인 성공 세션 정상 입력 (컨트롤러 작동)");
            return "redirect:/project/projectList";
        } else {
            //로그인 실패 알림창을 만들어줘야함
            log.info("로그인 실패 세션 적용 실패 (컨트롤러 작동)");
            return "user/login";
        }
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        //세션으로 로그아웃 처리
        session.invalidate();
        log.info("로그아웃 성공 세션 정상 작동(컨트롤러)");
        return "user/index";
    }


    //프로필로 가는 메서드 세션값을 활용해서 user의 정보를 찾아낸다
    @GetMapping("/user/{id}")
    public String goProfile(HttpSession session, Model model) {
        UserDto sessionUser = (UserDto) session.getAttribute("userInfo");
        UserDto result = userService.findByUser(sessionUser.getUuid());
        if (result != null) {
            model.addAttribute("user", sessionUser);
            log.info("회원정보 찾기 성공 (컨트롤러 작동) detail 페이지로 이동");
            return "user/detail";
        } else {
            log.info("서비스에서 유저를 찾지 못함 (컨트롤러 작동)");
            return null;
        }
    }

    //프로필에서 정보 변경 시 유저의 정보를 찾아오는 메서드
    @GetMapping("/user/update")
    public String updateForm(HttpSession session, Model model) {
        UserDto sessionUser = (UserDto) session.getAttribute("userInfo");
        UserDto userDto = userService.updateForm(sessionUser.getUuid());
        model.addAttribute("updateUserInfo", userDto);
        return "user/update";
    }

    //회원 정보 변경 시 메서드
    @PostMapping("/user/update")
    public String update(@RequestParam("email") String email,
                         @RequestParam("password") String password,
                         @RequestParam("username") String name, HttpSession session) {
        UserDto sessionUser = (UserDto) session.getAttribute("userInfo");
        log.info("변경 전 정보 " + sessionUser.toString());

        userService.update(sessionUser.getUuid(), email ,password, name);
        log.info("정상 업데이트 되었습니다 (컨트롤러 작동)");
        return "redirect:/user/" + updateUser.getUuid();
    }

    @GetMapping("/user/delete/{id}")
    public String deleteById(@PathVariable String id) {
        userService.deleteById(id);
        log.info("탈퇴되었습니다 (컨트롤러 작동)");
        return "redirect:/index";
    }

    //로그인을 성공 했을 떄 redirect로 session 값을 같이 가져와야함 (현재 session에는 로그인된 유저의 정보를 담고있어야한다)
    @GetMapping("/project/projectList")
    public String goProjectList(HttpSession session, Model model) {
        UserDto sessionUser = (UserDto) session.getAttribute("userInfo");

    }

    //Proejct Create 창으로 넘어가기 다음 내용은 ProjectController에서 계속 이어나감
    @GetMapping("/project/createPage")
    public String goToCreateProject() {
        return "project/home";
    }


}
