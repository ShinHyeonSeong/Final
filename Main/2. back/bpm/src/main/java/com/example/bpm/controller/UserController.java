package com.example.bpm.controller;

import com.example.bpm.dto.UserDto;
import com.example.bpm.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
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
            return "projectList page로 가야함";
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

    /*여기까지가 04.07 User 기능 최신화 */

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
        //현재 적용되어 있는 session 이름이 - loginInfo 이다 userDto 객체로 저장해서 id 값을 불러와야함
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
    /*  여기까지가 User 단순 CRUD */


    /*로그인 후 프로젝트 리스트 창*/
    //로그인을 성공 했을 떄 redirect로 session 값을 같이 가져와야함 (현재 session에는 로그인된 유저의 정보를 담고있다)
    @PostMapping("/proejct/projectList")
    public String goProjectList(HttpSession session) {
        session.getAttribute("uuid");

    }

    //Proejct Create 창으로 넘어가기 다음 내용은 ProjectController에서 계속 이어나감
    @GetMapping("/project/createPage")
    public String goToCreateProject() {
        return "project/home";
    }


}
