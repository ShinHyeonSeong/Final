package com.example.bpm.service;

import com.example.bpm.dto.ProjectRequestDto;
import com.example.bpm.dto.ProjectRoleDto;
import com.example.bpm.entity.ProjectEntity;
import com.example.bpm.entity.ProjectRequestEntity;
import com.example.bpm.entity.ProjectRoleEntity;
import com.example.bpm.repository.ProjectRepository;
import com.example.bpm.repository.ProjectRequestRepository;
import com.example.bpm.repository.ProjectRoleRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Builder
//메서드 리턴타입은 무조건 Dto로 나오게 하자.
//파라미터는 String 형식으로 받기로 하자 (절대 객체단위로 받으면 안됨 헷갈리기도 하고 NULL 오류가 잘 생김)
public class ProjectSerivce {
    final private ProjectRequestRepository projectRequestRepository;
    final private ProjectRepository projectRepository;
    final private ProjectRoleRepository projectRoleRepository;

    //본인과 관련된 프로젝트 리스트를 리턴해주는 메서드
    public List<ProjectRoleDto> findAllToProjectList(Long roleId, String projectId) {
        if (roleId == 1) {
            List<ProjectRoleEntity> entityList = projectRoleRepository.selectToRoleList(Long.valueOf(1));
            List<ProjectRoleDto> dtoList = new ArrayList<>();
            for (ProjectRoleEntity projectRoleEntity :
                    entityList) {
                dtoList.add(ProjectRoleDto.toProjectRoleDto(projectRoleEntity));
            }
            log.info("권한자로 있는 프로젝트 리스트 출력(서비스 작동)");
            return dtoList;
        } else if (roleId == 2) {
            List<ProjectRoleEntity> entityList = projectRoleRepository.selectToRoleList(Long.valueOf(2));
            List<ProjectRoleDto> dtoList = new ArrayList<>();
            for (ProjectRoleEntity projectRoleEntity :
                    entityList) {
                dtoList.add(ProjectRoleDto.toProjectRoleDto(projectRoleEntity));
            }
            log.info("비권한자로 있는 프로젝트 리스트 출력(서비스 작동)");
            return dtoList;
        } else {
            log.info("roleId의 값이 잘못되었습니다 (서비스 작동)");
            return null;
        }
    }


    //프로젝트 초대를 보내는 메서드      리턴 데이터는 send 한 기록을 보여준다
    public ProjectRequestDto sendInvite(String sendUser, String recvUser, String projectId) {
        if (sendUser != null && recvUser != null) {
            projectRequestRepository.plusProjectRequest(sendUser, recvUser, projectId);
            log.info("친구 요청 정상 작동 (서비스 작동)");
            Optional<ProjectRequestEntity> projectRequestEntity
                    = projectRequestRepository.selectToRequsetRow(sendUser, recvUser, projectId);
            //만약 정상 친구요청이 되면 그 row을 확인할 수 있게 return 한다
            return ProjectRequestDto.toProjectRequestDto(projectRequestEntity.get());
        } else {
            log.info("Dto NULL 값 (서비스 작동)");
            return null;
        }
    }

    //초대를 확인하는 메서드 recvUUID로만 보여짐
    public List<ProjectRequestDto> findAllToRequestProject(String recvUUID) {
        List<ProjectRequestEntity> entityList = projectRequestRepository.selectToParticipantsList(recvUUID);
        List<ProjectRequestDto> dtoList = new ArrayList<>();

        for (ProjectRequestEntity projectRequestEntity :
                entityList) {
            dtoList.add(ProjectRequestDto.toProjectRequestDto(projectRequestEntity));
        }
        log.info("recvUUID를 이용한 초대 수신 메서드 정상작동 (서비스 작동)");
        return dtoList;
    }


    //초대 수락하는 메서드
    //서비스의 파라미터로 true false 값을 받아와도 되지만 파라미터가 ㅈㄴ 많으므로 컨트롤러에서 if 문을 거칠 필요가 있음 (코드 개더럽네)
    public ProjectRoleDto submitInvite(String sendUUID, String recvUUID, String projectId, Long input) {
        if (input == 1) {
            
        } else if (input == 2) {

        } else {
            log.info("수락 요청의 변수값이 잘못되었습니다. (서비스 작동)");
            return null;
        }
    }


}
