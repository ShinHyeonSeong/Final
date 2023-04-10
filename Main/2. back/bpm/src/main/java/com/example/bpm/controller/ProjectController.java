package com.example.bpm.controller;

import com.example.bpm.dto.ProjectRoleDto;
import com.example.bpm.dto.UserDto;
import com.example.bpm.service.ProjectSerivce;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
@Builder
public class ProjectController {
    @Autowired
    final private ProjectSerivce projectSerivce;

    //로그인을 성공 했을 떄 redirect로 session 값을 같이 가져와야함 (현재 session에는 로그인된 유저의 정보를 담고있어야한다)
    @GetMapping("/project/projectList")
    public String goProjectList(HttpSession session, Model model) {
        //세션에서 현재 로그인 되어있는 유저의 정보를 가져온다
        UserDto sessionUser = (UserDto) session.getAttribute("userInfo");
        //UUID를 활용하여 권한자 / 비권한자 프로젝트 리스트를 불러온다
        List<ProjectRoleDto> ManagerToProjectList = projectSerivce.findManagerToProjectList(sessionUser.getUuid());
        List<ProjectRoleDto> ParticipantsToProjectList = projectSerivce.findParticipantsToProjectList(sessionUser.getUuid());
        if (ManagerToProjectList.isEmpty()) {
            if (ParticipantsToProjectList.isEmpty()) {
                log.info("참여중인 리스트가 없음");
            } else {
                log.info("비권한자 리스트만 있음");
            }
        } else {
            if (ParticipantsToProjectList.isEmpty()) {
                log.info("권한자 리스트만 있음");
            } else {
                log.info("둘다 리스트 있음");
            }
        }
        //null 이던 데이터가 있던 창을 보여줘야한다.
        model.addAttribute("ListToM", ManagerToProjectList);
        model.addAttribute("ListToP", ParticipantsToProjectList);
        return "projectList 페이지로 ";
    }

    @GetMapping("/project/createPage")
    public String goToCreateProject() {
        return "project/home";
    }

    @PostMapping("/project/createPage")
    public String CreateProject() {
        return "";
    }
}
