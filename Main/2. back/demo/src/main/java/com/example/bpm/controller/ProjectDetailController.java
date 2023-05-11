package com.example.bpm.controller;

import com.example.bpm.entity.HeadEntity;
import com.example.bpm.service.ProjectDetailSerivce;
import com.example.bpm.service.ProjectSerivce;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@ToString
@RequiredArgsConstructor
public class ProjectDetailController {
    @Autowired
    ProjectDetailSerivce projectDetailSerivce;

    @Autowired
    ProjectSerivce projectSerivce;

    /* - - - - 목표 관련 메서드- - - -*/
    @GetMapping("/project/createHead")
    public String goCreateHead(){
        return "goal-create";
    }
    @PostMapping("/project/createHead")
    public String createHead(@ModelAttribute HeadEntity headEntity){

    }

    /* - - - - 목표 관련 메서드 끝 - - - -*/

    /* - - - - 작업 관련 메서드- - - -*/

    /* - - - - 작업 관련 메서드 끝 - - - -*/

    /* - - - - 캘린더 관련 메서드- - - -*/

    /* - - - - 캘린더 관련 메서드 끝 - - - -*/

}

