package com.example.bpm.controller;

import com.example.bpm.dto.*;
import com.example.bpm.repository.UserRepository;
import com.example.bpm.service.ProjectDetailSerivce;
import com.example.bpm.service.ProjectSerivce;
//import jakarta.servlet.http.HttpSession;
import com.example.bpm.service.UserService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@Builder
public class ProjectController {

    @Autowired
    final private ProjectSerivce projectSerivce;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectDetailSerivce projectDetailSerivce;

    HttpSession session;
    public UserDto getSessionUser() {
        UserDto currentUser = (UserDto) session.getAttribute("userInfo");
        return currentUser;
    }

    public ProjectDto getSessionProject() {
        ProjectDto currentProject = (ProjectDto) session.getAttribute("currentProject");
        return currentProject;
    }



    public Long checkAuth() {
        ProjectDto projectDto = getSessionProject();
        UserDto userDto = getSessionUser();
        Long auth = userService.checkRole(projectDto.getProjectId(), userDto.getUuid());
        return auth;
    }

    // 관리자 권한 프로젝트 리스트 출력
    @GetMapping("/project/projectManagerList")
    public String getProjectList(HttpSession session, Model model) {
        //세션에서 현재 로그인 되어있는 유저의 정보를 가져온다
        UserDto sessionUser = (UserDto) session.getAttribute("userInfo");
        //UUID를 활용하여 권한자 / 비권한자 프로젝트 리스트를 불러온다
        List<ProjectDto> ManagerToProjectList = projectSerivce.findManagerToProjectList(sessionUser.getUuid());
        model.addAttribute("user", sessionUser);
        model.addAttribute("managerList", ManagerToProjectList);

        List<ProjectRequestDto> requestDtos = projectSerivce.findAllToRequestProject(sessionUser.getUuid());
        if (requestDtos.isEmpty()) {
            model.addAttribute("request", false);
        } else model.addAttribute("request", true);
        // 초대 여부 검사 후 프론트에서 시각적 알림 효과 부여

//        List<ProjectRoleDto> ParticipantsToProjectList = projectSerivce.findParticipantsToProjectList(sessionUser.getUuid());
//        if (ManagerToProjectList.isEmpty()) {
//            if (ParticipantsToProjectList.isEmpty()) {
//                log.info("참여중인 리스트가 없음");
//            } else {
//                log.info("비권한자 리스트만 있음");
//            }
//        } else {
//            if (ParticipantsToProjectList.isEmpty()) {
//                log.info("권한자 리스트만 있음");
//            } else {
//                log.info("둘다 리스트 있음");
//            }
//        }
//        //null값이던 데이터가 존재하던 어쨋든 리스트 창을 보여줘야한다. (빈 공백이라도)
//        model.addAttribute("ListToM", ManagerToProjectList);
//        model.addAttribute("ListToP", ParticipantsToProjectList);
        return "projectManagerList";
    }

    // 프로젝트 멤버 권한 리스트 출력
    @GetMapping("/project/projectMemberList")
    public String projectMemberList(HttpSession session, Model model) {
        //세션에서 현재 로그인 되어있는 유저의 정보를 가져온다
        UserDto sessionUser = getSessionUser();
        //UUID를 활용하여 권한자 / 비권한자 프로젝트 리스트를 불러온다
        List<ProjectDto> memberToProjectList = projectSerivce.findParticipantsToProjectList(sessionUser.getUuid());
        model.addAttribute("user", sessionUser);
        model.addAttribute("memberList", memberToProjectList);

        List<ProjectRequestDto> requestDtos = projectSerivce.findAllToRequestProject(sessionUser.getUuid());
        if (requestDtos.isEmpty()) {
            model.addAttribute("request", false);
        } else model.addAttribute("request", true);
        return "projectMemberList";
    }

    /*// 전체 프로젝트 리스트 출력
    @GetMapping("/project/projectAllList")
    public String projectAllList(Model model){
        UserDto nowUser = getSessionUser();
        List<ProjectDto> AllProjectList = projectSerivce.findAllToProjectList();
        model.addAttribute("projectAllList", AllProjectList);

            }
*/

    @GetMapping("/project/lunch")
    public String lunchProject() {
        return "projectLunch";
    }

    @GetMapping("/project/create")
    public String projectCreate() {
        return "projectCreate";
    }


    //프로젝트 생성 버튼을 누르는 순간 프로젝트 생성되는 메서드
    @PostMapping("/project/createPage")
    public String createProject(@ModelAttribute ProjectDto projectDto, HttpSession session, Model model) {
        if (projectDto.equals(null)) {
            log.info("값을 다 입력하지 못했음 (컨트롤러 작동)");
            return "projectCreate";
        } else {
            ProjectDto dto = projectSerivce.createProject(projectDto);
            log.info(dto.getProjectId().toString());
            UserDto sessionUser = getSessionUser();
            session.setAttribute("currentProject", projectDto);
            projectSerivce.autorization(dto, sessionUser);
            log.info("프로젝트 생성 정상 작동(컨트롤러 작동)");
            return "redirect:/project/projectManagerList";
        }
    }

    //프로젝트 선택 시 그 프로젝트 정보를 가져오며 프로젝트 창으로 넘어가는 메서드
    @RequestMapping("/project/{id}")
    public String selectProject(@PathVariable("id") Long id, HttpSession session, Model model) {
        ProjectDto presentDto = projectSerivce.selectProject(id);
        List<UserDto> userDtoList = userService.searchUserToProject(id);
        session.removeAttribute("currentProject");
        session.setAttribute("currentProject", presentDto);
        model.addAttribute("projectDto", presentDto);
        model.addAttribute("joinUsers", userDtoList);
        return "projectMain";
    }

    //전체 프로젝트 리스트에서 프로젝트 선택 시 해당 소개, 목표,
    @RequestMapping("/projectAll/{id}")
    public String selectAllProject(@PathVariable("id") Long id, HttpSession session, Model model){
        ProjectDto presentDto = projectSerivce.selectProject(id);
        List<UserDto> userDtoList = userService.searchUserToProject(id);
        List<HeadDto> headDtoList = projectDetailSerivce.selectAllHead(projectSerivce.selectProject(id));
        List<DetailDto> detailDtoList =
    }



    // 프로젝트 초대 확인창
    @GetMapping("/project/inviteList")
    public String inviteList(HttpSession session, Model model) {
        UserDto sessionUser = (UserDto) session.getAttribute("userInfo");
        //UUID를 활용하여 권한자 / 비권한자 프로젝트 리스트를 불러온다
        model.addAttribute("user", sessionUser);

        List<ProjectRequestDto> requestDtos = projectSerivce.findAllToRequestProject(sessionUser.getUuid());
        if (requestDtos.isEmpty()) {
            model.addAttribute("request", false);
        } else {
            model.addAttribute("request", true);
            model.addAttribute("requestList", requestDtos);
        }
        return "inviteList";
    }

    // 프로젝트 초대 발송 컨트롤러
    @RequestMapping("/project/invite/{id}")
    public String sendInvite(@PathVariable("id") String uuid, HttpSession session, Model model) {
        UserDto sendUser = (UserDto) session.getAttribute("userInfo");
        UserDto recvUser = UserDto.toUserDto(userRepository.findById(uuid).orElse(null));
        ProjectDto projectDto = (ProjectDto) session.getAttribute("currentProject");
        projectSerivce.sendInvite(sendUser.getUuid(), recvUser.getUuid(), projectDto.getProjectId());
        return "redirect:/user/search";
    }

    // 프로젝트 초대 수락, 거절 컨트롤러
    // @PathVariable 통해 전달하여 url 노출됨. 추후 재고
    @RequestMapping("/requestResponse/{sendUser}/{recvUser}/{project}/{acceptable}")
    public String requestResponse(@PathVariable("sendUser") String sendUuid,
                                  @PathVariable("recvUser") String recvUuid,
                                  @PathVariable("project") Long projectId,
                                  @PathVariable("acceptable") boolean acceptable) {
        log.info("전달 완료, " + sendUuid + recvUuid + projectId + acceptable);
        projectSerivce.submitInvite(sendUuid, recvUuid, projectId, acceptable);
        return "redirect:/project/inviteList";
    }

    @GetMapping("/project/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectSerivce.deleteProject(id);
        log.info("프로젝트 정상 삭제 (컨트롤러 작동)");
        return "redirect:/project/projectList";
    }


    /* - - - - - - onlyReadPage 접근 - - - - - - */

}

