package com.example.bpm.service;

import com.example.bpm.dto.project.ProjectDto;
import com.example.bpm.dto.project.request.ProjectRequestDto;
import com.example.bpm.dto.project.relation.ProjectRoleDto;
import com.example.bpm.dto.user.UserDto;
import com.example.bpm.entity.project.data.ProjectEntity;
import com.example.bpm.entity.project.request.ProjectRequestEntity;
import com.example.bpm.entity.project.relation.ProjectRoleEntity;
import com.example.bpm.entity.project.data.RoleEntity;
import com.example.bpm.entity.user.UserEntity;
import com.example.bpm.repository.*;
import com.example.bpm.service.Logic.dateLogic.DateManager;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@NoArgsConstructor(force = true)
public class ProjectSerivce {
    @Autowired
    final private ProjectRequestRepository projectRequestRepository;
    @Autowired
    final private ProjectRepository projectRepository;
    @Autowired
    final private ProjectRoleRepository projectRoleRepository;
    @Autowired
    final private RoleRepository roleRepository;

    DateManager dateManager = new DateManager();

    /*Request Table 관련 기능*/

    //프로젝트 초대를 보내는 메서드      리턴 데이터는 send 한 기록을 보여준다
    public void sendInvite(String sendUser, String recvUser, Long projectId) {
        if (sendUser != null && recvUser != null) {
            projectRequestRepository.plusProjectRequest(sendUser, recvUser, projectId);
        }
    }

    //초대를 확인하는 메서드 recvUUID로만 보여짐
    public List<ProjectRequestDto> findAllToRequestProject(String recvUUID) {
        List<ProjectRequestEntity> entityList = projectRequestRepository.selectParticipantsList(recvUUID);
        List<ProjectRequestDto> dtoList = new ArrayList<>();

        for (ProjectRequestEntity projectRequestEntity :
                entityList) {

            ProjectRequestDto projectRequestDto = new ProjectRequestDto();
            projectRequestDto.insertEntity(projectRequestEntity);

            dtoList.add(projectRequestDto);
        }
        log.info("recvUUID를 이용한 초대 수신 메서드 정상작동 (서비스 작동)");
        return dtoList;
    }

    //초대 수락하는 메서드(무조건 비권한자로써 수락받는다)
    //서비스의 파라미터로 true false 값을 받아와도 되지만 파라미터가 ㅈㄴ 많으므로 컨트롤러에서 if 문을 거칠 필요가 있음 (코드 개더럽네)
    public int submitInvite(String sendUUID, String recvUUID, Long projectId, boolean input) {
        //수락
        if (input) {
            //ProjectRquest에 있는 데이터 삭제
            projectRequestRepository.deleteByAllId(sendUUID, recvUUID, projectId);
            log.info("수락 요청으로 인한 요청테이블 데이터 삭제 작동 (서비스 작동)");
            //ProejctRole Table에 데이터 추가
            ProjectRoleEntity projectRoleEntity = projectRoleRepository.insertToRoleEntity(projectId, recvUUID, Long.valueOf(0));
            log.info("수락 요청으로 인한 Role Table에 데이터 삽입 (서비스 작동)");
            return 1;
        }
        //거절
        else if (!input) {
            //ProjectRquest에 있는 데이터 삭제
            projectRequestRepository.deleteByAllId(sendUUID, recvUUID, projectId);
            log.info("거절 요청으로 인한 요청테이블 데이터 삭제 작동 (서비스 작동)");
            return 1;
        } else {
            log.info("수락 요청의 변수값이 잘못되었습니다. (서비스 작동)");
            return 0;
        }
    }
    /*Request Table 관련 메서드 끝*/

    /*ProjectRole + Project Table 관련 기능*/

    public List<ProjectDto> findManagerToProjectList(String userId) {
        //List<ProjectRoleEntity> entityList = projectRoleRepository.findByAlltoUserId(userId);
        List<ProjectRoleEntity> entityList = projectRoleRepository.userForRole(userId);
        log.info("uuid를 통한 관리자 권한 프로젝트 조회");
        List<ProjectDto> dtoListToM = new ArrayList<>();
        for (ProjectRoleEntity projectRoleEntity :
                entityList) {
            //1 == 권한자 (프로젝트 생성자)
            if (projectRoleEntity.getRole().getId() == 1) {
                log.info("관리자 권한으로 된 프로젝트가 있음 (서비스 작동), " + projectRoleEntity.getProjectIdInRole().getTitle());
                ProjectDto dto = new ProjectDto();
                dto.insertEntity(projectRepository.findById(projectRoleEntity.getProjectIdInRole().getProjectId()).orElse(null));
                dtoListToM.add(dto);
            }
        }
        if (dtoListToM.isEmpty()) {
            log.info("관리자 권한으로 된 프로젝트가 없음 (서비스 작동)");
            return null;
        } else {
            return dtoListToM;
        }
    }

    //본인이 참여하고있는 비권한자 프로젝트 리스트를 리턴해주는 메서드 (프로젝트 리스트 페이지에서 필요함)
    public List<ProjectDto> findParticipantsToProjectList(String userId) {
        List<ProjectRoleEntity> entityList = projectRoleRepository.userForRole(userId);
        List<ProjectDto> dtoListToP = new ArrayList<>();
        for (ProjectRoleEntity projectRoleEntity :
                entityList) {
            //2 == 비권한자 (프로젝트 참여자)
            if (projectRoleEntity.getRole().getId() == 0) {
                log.info("비관리자 권한으로 된 프로젝트가 있음 (서비스 작동)");
                ProjectDto dto = new ProjectDto();
                dto.insertEntity(projectRepository.findById(projectRoleEntity.getProjectIdInRole().getProjectId()).orElse(null));
                dtoListToP.add(dto);
            }
        }
        if (dtoListToP.isEmpty()) {
            log.info("비관리자 권한으로 된 프로젝트가 없음 (서비스 작동)");
            return null;
        } else {
            return dtoListToP;
        }
    }

    //전체 프로젝트 리스트 가져오는 메서드
    public List<ProjectDto> findAllToProjectList(){
        List<ProjectEntity> entityList = projectRepository.findAll();
        List<ProjectDto> dtoList = new ArrayList<>();
        for (ProjectEntity projectEntity : entityList) {
            ProjectDto projectDto = new ProjectDto();
            projectDto.insertEntity(projectEntity);
            dtoList.add(projectDto);
        }
        return dtoList;
    }


    //관리자 프로젝트 권한 부여 메서드
    public ProjectRoleDto autorization(ProjectDto projectDto, UserDto userDto) {
        ProjectEntity projectEntity = projectDto.toEntity();

        UserEntity userEntity = userDto.toEntity();
        RoleEntity roleEntity = roleRepository.findById(Long.valueOf(1)).orElse(null);
        log.info("권한 부여 메서드 실행 중 " + projectEntity.getProjectId() + ", " + userEntity.getUuid() + ", " + roleEntity.getRoleName());

        ProjectRoleDto projectRoleDto = new ProjectRoleDto(projectEntity, userEntity, roleEntity);

        ProjectRoleEntity projectRoleEntity = projectRoleDto.toEntity();
        log.info("projectRoleEntity 생성 완료");
        log.info("프로젝트 생성자 권한 부여" + projectRoleEntity.getProjectIdInRole().getProjectId() + ", " +
                projectRoleEntity.getUuidInRole().getUuid() + ", " + projectRoleEntity.getRole().getRoleName());
        projectRoleRepository.save(projectRoleEntity);
        return ProjectRoleDto.toProjectRoleDto(projectRoleEntity);
    }

    //생성 메서드
    public ProjectDto createProject(String title, String subtitle, String startDay, String endDay) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setTitle(title);
        projectDto.setSubtitle(subtitle);
        projectDto.setStartDay(dateManager.formatter(startDay));
        projectDto.setEndDay(dateManager.formatter(endDay));
        ProjectEntity projectEntity = projectRepository.save(projectDto.toEntity());
        log.info("프로젝트 정상 생성 (서비스 작동)");
        projectDto.insertEntity(projectEntity);
        return projectDto;
    }

    //프로젝트 접속하는 메서드
    public ProjectDto selectProject(Long projectId) {
        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectId);
        ProjectDto projectDto = new ProjectDto();
        projectDto.insertEntity(projectEntity.get());
        return projectDto;
    }


}
