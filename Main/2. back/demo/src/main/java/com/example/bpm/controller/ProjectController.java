package com.example.bpm.controller;

import com.example.bpm.dto.ProjectDto;
import com.example.bpm.dto.ProjectRequestDto;
import com.example.bpm.dto.UserDto;
import com.example.bpm.repository.UserRepository;
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

    //로그인을 성공 했을 떄 redirect로 session 값을 같이 가져와야함 (현재 session에는 로그인된 유저의 정보를 담고있어야한다)
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

    @GetMapping("/project/projectMemberList")
    public String projectMemberList(HttpSession session, Model model) {
        //세션에서 현재 로그인 되어있는 유저의 정보를 가져온다
        UserDto sessionUser = (UserDto) session.getAttribute("userInfo");
        //UUID를 활용하여 권한자 / 비권한자 프로젝트 리스트를 불러온다
        List<ProjectDto> ManagerToProjectList = projectSerivce.findParticipantsToProjectList(sessionUser.getUuid());
        model.addAttribute("user", sessionUser);
        model.addAttribute("managerList", ManagerToProjectList);

        List<ProjectRequestDto> requestDtos = projectSerivce.findAllToRequestProject(sessionUser.getUuid());
        if (requestDtos.isEmpty()) {
            model.addAttribute("request", false);
        } else model.addAttribute("request", true);
        return "projectMemberList";
    }

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
            UserDto sessionUser = (UserDto) session.getAttribute("userInfo");
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
        // 프로젝트에 참여중인 유저 검색
        session.removeAttribute("currentProject");
        session.setAttribute("currentProject", presentDto);
        model.addAttribute("projectDto", presentDto);
        model.addAttribute("joinUsers", userDtoList);
        //프로젝트 id 값을 넘겨야되는데

        return "projectMain";
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
        return "redirect:/searchMemberResult";
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

}
