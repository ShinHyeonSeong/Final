package com.example.bpm.controller;

import com.example.bpm.dto.ProjectDto;
import com.example.bpm.service.ProjectSerivce;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

<<<<<<< HEAD
//@Controller
//@Slf4j
//@Builder
//public class ProjectController {
//    final private ProjectSerivce;
//
//}
=======
@Controller
@Slf4j
@Builder
public class ProjectController {
    @Autowired
    final private ProjectSerivce projectSerivce;


    //프로젝트 생성
    @PostMapping("/project/createProject")
    public String createProject(@ModelAttribute ProjectDto projectDto) {


        return "project/projectCreate";
    }

    //프로젝트 안에서 초대 보내기
    @PostMapping("/")

}
>>>>>>> upstream/master
