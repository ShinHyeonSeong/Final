package com.example.bpm.controller;

import com.example.bpm.dto.*;
import com.example.bpm.entity.Document;
import com.example.bpm.entity.UserEntity;
import com.example.bpm.entity.WorkEntity;
import com.example.bpm.service.DocumentService;
import com.example.bpm.service.ProjectDetailSerivce;
import com.example.bpm.service.ProjectSerivce;
import com.example.bpm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@ToString
@RequiredArgsConstructor
public class ProjectDetailController {
    @Autowired
    ProjectDetailSerivce projectDetailSerivce;
    @Autowired
    ProjectSerivce projectSerivce;
    @Autowired
    DocumentService documentService;
    @Autowired
    UserService userService;
    @Autowired
    private HttpSession session;

    // 생각을 해보니 말야 매번 세션 호출하는것보다는 그냥 따로 메서드 만드는게 훨씬 효율이 좋을듯. 편하기도 하고
    public UserDto getSessionUser() {
        UserDto currentUser = (UserDto) session.getAttribute("userInfo");
        return currentUser;
    }

    public ProjectDto getSessionProject() {
        ProjectDto currentProject = (ProjectDto) session.getAttribute("currentProject");
        return currentProject;
    }

    // 세션 유저 권한 확인
    public Long checkAuth() {
        ProjectDto projectDto = getSessionProject();
        UserDto userDto = getSessionUser();
        Long auth = userService.checkRole(projectDto.getProjectId(), userDto.getUuid());
        return auth;
    }

    // 프로젝트 사이드바 및 내부 get 매핑
    @GetMapping("/project/main")
    public String projectMain() {
        ProjectDto currentProject = getSessionProject();
        return "projectMain";
    }

    @GetMapping("/project/goals")
    public String goals(Model model) {
        ProjectDto currentProject = getSessionProject();
        List<HeadDto> headDtoList = projectDetailSerivce.selectAllHead(currentProject);
        Long auth = checkAuth();
        model.addAttribute("headDtoList", headDtoList);
        model.addAttribute("auth", auth);
        return "goal";
    }

    // 상위 목표 생성 진입
    @GetMapping("/project/head/create")
    public String goHeadDetail(Model model) {
        return "head-create";
    }

    // 하위 목표 생성 진입
    @GetMapping("/project/detail/create")
    public String goCreateDetail(Model model) {
        ProjectDto currentProject = getSessionProject();
        List<HeadDto> headDtoList = projectDetailSerivce.selectAllHead(currentProject);
        model.addAttribute("headDtoList", headDtoList);
        return "detail-create";
    }

    @GetMapping("/project/works")
    public String works(Model model) {
        UserDto currentUser = getSessionUser();
        ProjectDto currentProject = getSessionProject();
        List<WorkDto> userWorkDtoList = projectDetailSerivce.selectAllWorkForUser(currentUser);
        List<WorkDto> projectWorkDtoList = projectDetailSerivce.selectAllWorkForProject(currentProject);
        Long auth = checkAuth();
        if (projectWorkDtoList != null) {
            model.addAttribute("projectWorkDtoList", projectWorkDtoList);
        }
        model.addAttribute("userWorkDtoList", userWorkDtoList);
        model.addAttribute("auth", auth);
        return"work";
    }

    @GetMapping("/project/work/create")
    public String goCreateWork(Model model) {
        ProjectDto currentProject = getSessionProject();
        List<UserDto> userDtoList = userService.searchUserToProject(currentProject.getProjectId());
        List<DetailDto> detailDtoList = projectDetailSerivce.selectAllDetailForProject(currentProject);
        model.addAttribute("userDtoList", userDtoList);
        model.addAttribute("detailDtoList", detailDtoList);
        return "workCreate";
    }

    @RequestMapping("/project/work/detail/{id}")
    public String goWorkDetail(@PathVariable("id")Long id, Model model) {
        WorkDto workDto = projectDetailSerivce.selectWork(id);
        UserDto userDto = projectDetailSerivce.selectUserForUserWork(workDto);
        List<DocumentDto> documentDtoList = documentService.getDocumentByWorkId(id);
        Long auth = checkAuth();

        model.addAttribute("workDto", workDto);
        model.addAttribute("userDto", userDto);
        model.addAttribute("DocumentList", documentDtoList);
        model.addAttribute("auth", auth);
        return "workDetail";
    }
    
    /* - - - - 목표 관련 메서드- - - -*/
    // 목표 생성
    /* 클라이언트에서 전달 받을 때, Dto 내부 속성 중 전달받을 수 없는 속성들이 있다. 때문에 @ModelAttribute를 쓰지 않고 하나씩 전달 받은 후
        서비스 단위로 때려박았음. 너무 보기 복잡하기도 하고 좀 그런데 보면서 다른 방법이 있으면 얘기해주면 좋게따. */
    @PostMapping("/project/goal/createHead")
    public String createGoal(@RequestParam(value = "title") String title,
                             @RequestParam(value = "deadline") String deadline,
                             @RequestParam(value = "discription") String discription,
                             Model model) {
        ProjectDto currentProject = getSessionProject();
        log.info("목표 생성 컨트롤러 작동, ");
        HeadDto createHeadDto = projectDetailSerivce.createHead(title, deadline, discription, currentProject);
        return "redirect:/main";
    }

    @PostMapping("/project/goal/createDetail")
    public String createGoal(@RequestParam(value = "title") String title,
                             @RequestParam(value = "deadline") String deadline,
                             @RequestParam(value = "discription") String discription,
                             @RequestParam(value = "headId") Long headId,
                             Model model) {
        ProjectDto currentProject = getSessionProject();
        log.info("목표 생성 컨트롤러 작동, ");
        if (headId == 0) {
            log.info("headDto == null");
            HeadDto createHeadDto = projectDetailSerivce.createHead(title, deadline, discription, currentProject);
        } else if (headId != 0) {
            HeadDto headDto = projectDetailSerivce.selectHead(headId);
            log.info("headDto.get" + headDto.getHeadId());
            DetailDto createDetailDto = projectDetailSerivce.createDetail(title, deadline, discription, headDto, currentProject);
        }
        return "redirect:/main";
    }

    // 목표 디테일 창 이동 메서드
    @RequestMapping("/project/goal/headView/{id}")
    public String goHeadView(@PathVariable("id")Long id, Model model) {
        HeadDto headDto = projectDetailSerivce.selectHead(id);
        List<DetailDto> detailDtoList = projectDetailSerivce.selectAllDetailForHead(headDto);
        Long auth = checkAuth();
        model.addAttribute("headDto", headDto);
        model.addAttribute("connectDetailList", detailDtoList);
        model.addAttribute("auth", auth);
        return "headView";
    }

    @RequestMapping("/project/goal/detailView/{id}")
    public String goDetailView(@PathVariable("id")Long id, Model model) {
        DetailDto detailDto = projectDetailSerivce.selectDetail(id);
        HeadDto headDto = projectDetailSerivce.selectHead(detailDto.getHeadIdToDetail().getHeadId());
        List<WorkDto> workDtoList = projectDetailSerivce.selectAllWorkForDetail(id);
        Map<WorkDto, UserDto> userWorkMap = projectDetailSerivce.selectAllUserWorkForWorkList(workDtoList);
        Long auth = checkAuth();
        model.addAttribute("detailDto", detailDto);
        model.addAttribute("headDto", headDto);
        model.addAttribute("workDtoList", workDtoList);
        model.addAttribute("userWorkMap", userWorkMap);
        model.addAttribute("auth", auth);
        return "detailView";
    }



    /* - - - - 목표 관련 메서드 끝 - - - -*/

    /* - - - - 작업 관련 메서드- - - -*/
    @PostMapping("/project/work/createWork")
    public String createWork(@RequestParam("title") String title,
                             @RequestParam("discription") String discription,
                             @RequestParam("deadline") String deadline,
                             @RequestParam("chargeUser") String uuid,
                             @RequestParam("connectDetail") Long detailId) {
        ProjectDto currentProject = getSessionProject();
        UserDto chargeUser = userService.findByUser(uuid);
        DetailDto connectDetail = projectDetailSerivce.selectDetail(detailId);
        WorkDto createWorkDto = projectDetailSerivce.createWork(title, discription, deadline,
                connectDetail, currentProject);
        log.info("작업 생성 메서드 완료, id = " + createWorkDto.getWorkId());
        projectDetailSerivce.addUserWork(createWorkDto, chargeUser);
        return "redirect:/project/works";
    }

    /* - - - - 작업 관련 메서드 끝 - - - -*/

    /* - - - - 댓글 관련 메서드 - - - -*/
    @PostMapping("댓글작성 클릭 시")
    public String plusComment(@RequestParam("workId") Long workId,
                              @RequestParam("comment") String comment,
                              HttpSession session, Model model) {
        /* 댓글을 추가 시키는 메서드 */
        WorkDto workDto = projectDetailSerivce.selectWork(workId);
        UserDto nowUser = getSessionUser();
        WorkCommentDto workCommentDto = new WorkCommentDto();
        workCommentDto.setComment(comment);
        workCommentDto.setWorkIdToComment(WorkEntity.toWorkEntity(workDto));
        workCommentDto.setUserIdToComment(UserEntity.toUserEntity(nowUser));
        /* 댓글을 추가 시키는 메서드 끝 */

        /*추가 시킬 댓글 내용과, 현재 documentID 를 같이 넘겨 리턴 값으로 자동 리스트를 뽑아온다*/
        List<WorkCommentDto> list = projectDetailSerivce.plusComment(workCommentDto, workId);
        model.addAttribute("commentList", list);
        return ""
    }

    //댓글 수정을 하기 위한 댓글 데이터를 가져오는 메서드 (프론트에서는 댓글을 수정할 수 있는 화면이 필요하다
    @GetMapping("댓글 수정 클릭 시")
    public String updateForm(@RequestParam("commentId") Long commentId, Model model) {
        WorkCommentDto updateComment = projectDetailSerivce.findComment(commentId);
        model.addAttribute("updateComment", updateComment);

        return ""
    }

    @PostMapping("댓글 수정 완료 시")
    public String updateComment(@RequestParam("workId") Long workId,
                                @RequestParam("comment") String comment, HttpSession session, Model model) {

        WorkDto workDto = projectDetailSerivce.selectWork(workId);
        UserDto nowUser = getSessionUser();
        WorkCommentDto workCommentDto = new WorkCommentDto();
        workCommentDto.setComment(comment);
        workCommentDto.setWorkIdToComment(WorkEntity.toWorkEntity(workDto));
        workCommentDto.setUserIdToComment(UserEntity.toUserEntity(nowUser));

        List<WorkCommentDto> list = projectDetailSerivce.plusComment(workCommentDto, workId);
        model.addAttribute("commentList", list);
        return ""
    }

    @GetMapping(" 삭제 클릭 시 ")
    public String deleteComment(@RequestParam("workId") Long workId, Model model) {
        List<WorkCommentDto> dtoList = projectDetailSerivce.deleteComment(workId);
        model.addAttribute("commentList", dtoList);
        return ""
    }
    /* - - - - 댓글 관련 메서드 끝 - - - -*/


}

