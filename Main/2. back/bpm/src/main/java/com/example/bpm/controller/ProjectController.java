package com.example.bpm.controller;

import com.example.bpm.dto.ProjectDto;
import com.example.bpm.dto.UserDto;
import com.example.bpm.service.ProjectSerivce;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@Builder
public class ProjectController {
    @Autowired
    final private ProjectSerivce projectSerivce;

    //로그인을 성공 했을 떄 redirect로 session 값을 같이 가져와야함 (현재 session에는 로그인된 유저의 정보를 담고있어야한다)
    @GetMapping("/project/projectList")
    public String goProjectList(HttpSession session, Model model) {
        UserDto sessionUser = (UserDto) session.getAttribute("userInfo");
        projectSerivce.findAllToProjectList()
        return
    }
    //Proejct Create 창으로 넘어가기 다음 내용은 ProjectController에서 계속 이어나감

    @GetMapping("/project/createPage")
    public String goToCreateProject() {
        return "project/home";
    }


}
