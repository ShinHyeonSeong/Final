package com.example.bpm.service;

import com.example.bpm.repository.ProjectRepository;
import com.example.bpm.repository.ProjectRequestRepository;
import com.example.bpm.repository.ProjectRoleRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Builder
public class ProjectSerivce {
    final private ProjectRequestRepository projectRequestRepository;
    final private ProjectRepository projectRepository;
    final private ProjectRoleRepository projectRoleRepository;



}
