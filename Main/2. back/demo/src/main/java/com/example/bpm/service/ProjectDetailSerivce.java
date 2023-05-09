package com.example.bpm.service;

import com.example.bpm.entity.ProjectEntity;
import com.example.bpm.entity.WorkEntity;
import com.example.bpm.repository.HeadRepository;
import com.example.bpm.repository.ProjectRepository;
import com.example.bpm.repository.UserRepository;
import com.example.bpm.repository.WorkRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@NoArgsConstructor(force = true)
/* 해당 클래스는 프로젝트 안에서 편집이 이루어지고 목표 작업 등을 작성하고 document와의 연결을 구현한 클래스이다    */
public class ProjectDetailSerivce {

    @Autowired
    private final HeadRepository headRepository;
    @Autowired
    private final ProjectRepository projectRepository;
    @Autowired
    private final WorkRepository workRepository;
    @Autowired
    private final UserRepository userRepository;


}
