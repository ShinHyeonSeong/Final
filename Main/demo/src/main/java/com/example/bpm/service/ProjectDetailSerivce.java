package com.example.bpm.service;

import com.example.bpm.dto.*;
import com.example.bpm.entity.*;
import com.example.bpm.repository.*;
import com.example.bpm.service.dateLogic.DateManager;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
@NoArgsConstructor(force = true)
/* 해당 클래스는 프로젝트 안에서 편집이 이루어지고 목표 작업 등을 작성하고 document와의 연결을 구현한 클래스이다    */
public class ProjectDetailSerivce {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final ProjectRepository projectRepository;
    @Autowired
    private final HeadRepository headRepository;
    @Autowired
    private final DetailRepository detailRepository;
    @Autowired
    private final WorkRepository workRepository;
    @Autowired
    private UserWorkRepository userWorkRepository;
    @Autowired
    private WorkCommentRepository workCommentRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private WorkDocumentRepository workDocumentRepository;

    Date currentDate = new Date(); // 시작 날짜(현재) 생성
    DateManager dateManager = new DateManager();

    // 마감기한 문자열 Date 타입 반환 메서드. 문자열 형식 상이로 변환 실패시 null

    /* - - - - 생성 메서드 시작 - - - - */

    // head 생성 메서드
    public HeadDto createHead(String title, String startDate, String deadline, String discription, ProjectDto projectDto) {
        if (headRepository.findByTitle(title).isPresent()) {
            log.info("head title 이 이미 존재한다. (서비스)");
            return null;
        } else {
            log.info("createHead service 작동");
            Date startDay = dateManager.formatter(startDate);
            Date endDay = dateManager.formatter(deadline);
            if (endDay == null) return null;

            HeadDto createHeadDto = new HeadDto();
            createHeadDto.setTitle(title);
            createHeadDto.setDiscription(discription);
            createHeadDto.setStartDay(startDay);
            createHeadDto.setEndDay(endDay);
            createHeadDto.setCompletion(0);
            createHeadDto.setProjectIdToHead(projectRepository.findById(projectDto.getProjectId()).orElse(null));
            log.info("HeadDto 생성 완료");
            HeadEntity createHead = headRepository.save(HeadEntity.toHeadEntity(createHeadDto));
            log.info("HeadEntity 저장 및 생성 완료");
            return HeadDto.toHeadDto(createHead);
        }
    }

    // detail 생성 메서드
    public DetailDto createDetail(String title, String startDate, String deadline, String discription,
                                  HeadDto connectedHead, ProjectDto projectDto) {
        if (detailRepository.findByTitle(title).isPresent()) {
            log.info("detail title 이 이미 존재한다. (서비스)");
            return null;
        } else {
            log.info("createDetail service 작동");
            Date startDay = dateManager.formatter(startDate);
            Date endDay = dateManager.formatter(deadline);
            if (endDay == null) return null;

            DetailDto createDetailDto = new DetailDto();
            createDetailDto.setTitle(title);
            createDetailDto.setDiscription(discription);
            createDetailDto.setStartDay(startDay);
            createDetailDto.setEndDay(endDay);
            createDetailDto.setCompletion(0);
            createDetailDto.setHeadIdToDetail(headRepository.findById(connectedHead.getHeadId()).orElse(null));
            createDetailDto.setProjectIdToDetail(projectRepository.findById(projectDto.getProjectId()).orElse(null));
            log.info("DetailDto 생성 완료");
            DetailEntity createHead = detailRepository.save(DetailEntity.toDetailEntity(createDetailDto));
            log.info("DetailEntity 저장 및 생성 완료");
            return DetailDto.toDetailDto(createHead);
        }
    }

    //내 작업 만들기
    public WorkDto createWork(String title, String discription, String startDate, String deadline,
                              DetailDto connectDetail, ProjectDto projectDto) {
        if (workRepository.findByTitle(title).isPresent()) {
            log.info("work title 이 이미 존재한다. (서비스)");
            return null;
        } else {
            Date startDay = dateManager.formatter(startDate);
            Date endDay = dateManager.formatter(deadline);

            WorkDto createWorkDto = new WorkDto();
            createWorkDto.setTitle(title);
            createWorkDto.setDiscription(discription);
            createWorkDto.setStartDay(startDay);
            createWorkDto.setEndDay(endDay);
            createWorkDto.setCompletion(0);
            createWorkDto.setDetailIdToWork(detailRepository.findById(connectDetail.getDetailId()).orElse(null));
            createWorkDto.setProjectIdToWork(projectRepository.findById(projectDto.getProjectId()).orElse(null));
            log.info("work 생성 성공 (서비스)");
            WorkEntity createWork = workRepository.save(WorkEntity.toWorkEntity(createWorkDto));
            log.info("workEntity Id = " + createWork.getWorkId().toString());
            return WorkDto.toWorkDto(createWork);
        }
    }

    //유저 작업 테이블 추가 메서드
    public void addUserWork(WorkDto workDto, UserDto userDto) {
        log.info("user_work table insert method");
        WorkEntity workEntity = WorkEntity.toWorkEntity(workDto);
        UserEntity userEntity = UserEntity.toUserEntity(userDto);
        UserWorkEntity userWorkEntity = UserWorkEntity.toUserWorkEntity(workEntity, userEntity);
        userWorkRepository.save(userWorkEntity);
    }

    /* - - - - 생성 메서드 끝 - - - - */

    /* - - - - 선택 메서드 시작 - - - - - */

    public HeadDto selectHead(Long id) {
        Optional<HeadEntity> find = headRepository.findById(id);
        return HeadDto.toHeadDto(find.get());
    }

    public List<HeadDto> selectAllHead(ProjectDto projectDto) {
        List<HeadDto> headDtoList = new ArrayList<>();
        List<HeadEntity> headEntityList = headRepository.findAllByProjectIdToHead_ProjectId(projectDto.getProjectId());
        for (HeadEntity headEntity : headEntityList) {
            headDtoList.add(HeadDto.toHeadDto(headEntity));
        }
        return headDtoList;
    }

    public DetailDto selectDetail(Long id) {
        Optional<DetailEntity> find = detailRepository.findById(id);
        return DetailDto.toDetailDto(find.get());
    }

    public List<DetailDto> selectAllDetailForHead(HeadDto headDto) {
        List<DetailDto> detailDtoList = new ArrayList<>();
        List<DetailEntity> detailEntityList = detailRepository.findAllByHeadIdToDetail_HeadId(headDto.getHeadId());
        for (DetailEntity detailEntity : detailEntityList) {
            detailDtoList.add(DetailDto.toDetailDto(detailEntity));
        }
        return detailDtoList;
    }

    public List<DetailDto> selectAllDetailForProject(ProjectDto projectDto) {
        List<DetailDto> detailDtoList = new ArrayList<>();
        List<DetailEntity> detailEntityList = detailRepository.
                findAllByProjectIdToDetail_ProjectId(projectDto.getProjectId());
        for (DetailEntity detailEntity : detailEntityList) {
            detailDtoList.add(DetailDto.toDetailDto(detailEntity));
        }
        return detailDtoList;
    }

    public WorkDto selectWork(Long id) {
        Optional<WorkEntity> find = workRepository.findById(id);
        return WorkDto.toWorkDto(find.get());
    }

    public List<WorkDto> selectAllWorkForProject(ProjectDto projectDto) {
        List<WorkDto> workDtoList = new ArrayList<>();
        List<WorkEntity> workEntityList = workRepository.findAllByProjectIdToWork_ProjectId(projectDto.getProjectId());
        for (WorkEntity workEntity : workEntityList) {
            workDtoList.add(WorkDto.toWorkDto(workEntity));
        }
        return workDtoList;
    }

    public List<WorkDto> selectAllWorkForDetail(Long id) {
        List<WorkEntity> workEntityList = workRepository.findAllByDetailIdToWork_DetailId(id);
        List<WorkDto> workDtoList = new ArrayList<>();
        for (WorkEntity workEntity : workEntityList) {
            workDtoList.add(WorkDto.toWorkDto(workEntity));
        }
        return workDtoList;
    }

    public List<WorkDto> selectAllWorkForUser(UserDto userDto) {
        List<UserWorkEntity> userWorkList = userWorkRepository.findAllByUserIdToUserWork_Uuid(userDto.getUuid());
        List<WorkDto> workDtoList = new ArrayList<>();
        for(UserWorkEntity userWorkEntity : userWorkList) {
            workDtoList.add(WorkDto.toWorkDto(workRepository.findById(
                    userWorkEntity.getWorkIdToUserWork().getWorkId()).orElse(null)));
        }
        return workDtoList;
    }

    public UserDto selectUserForUserWork(WorkDto workDto) {
        UserWorkEntity userWorkEntity = userWorkRepository.findByWorkIdToUserWork_WorkId(workDto.getWorkId());
        Optional<UserEntity> userEntity = userRepository.findById(userWorkEntity.getUserIdToUserWork().getUuid());
        return UserDto.toUserDto(userEntity.get());
    }

    public Map<WorkDto, UserDto> selectAllUserWorkForWorkList(List<WorkDto> workDtoList) {
        Map<WorkDto, UserDto> userWorkMap = new HashMap<>();
        for (WorkDto workDto : workDtoList) {
            UserWorkEntity userWorkEntity = userWorkRepository.findByWorkIdToUserWork_WorkId(workDto.getWorkId());
            WorkDto workDtoKey = WorkDto.toWorkDto(userWorkEntity.getWorkIdToUserWork());
            UserDto userDtoValue = UserDto.toUserDto(userWorkEntity.getUserIdToUserWork());
            userWorkMap.put(workDtoKey, userDtoValue);
        }
        return userWorkMap;
    }

    // work list 매개변수를 통해 workDocument 테이블을 경유하여 workId에 대응하는 모든 document를 리스트로 반환
    public List<DocumentDto> selectAllDocumentForWorkList(List<WorkDto> workDtoList) {
        List<DocumentDto> documentDtoList = new ArrayList<>();
        for (WorkDto workDto : workDtoList) {
            List<WorkDocumentEntity> workDocumentEntityList = workDocumentRepository.findAllByWorkIdToWorkDocument_WorkId(workDto.getWorkId());
            for (WorkDocumentEntity workDocumentEntity : workDocumentEntityList) {
                DocumentDto documentDto = new DocumentDto();
                documentDto.insertEntity(documentRepository.findByDocumentId(workDocumentEntity.getDocumentIdToWorkDocument().getDocumentId()));
                documentDtoList.add(documentDto);
            }
        }
        return documentDtoList;
    }

    /* - - - - 선택 메서드 끝 - - - - - */

    /* - - - - 수정 메서드 시작 - - - - - */
    public HeadDto updateSelectHead(Long headId) {
        Optional<HeadEntity> find = headRepository.findById(headId);
        if (find.isEmpty()) {
            log.info("찾은 결과가 없음 (서비스)");
            return null;
        } else {
            log.info(find.get().getTitle() + "의 내용을 찾음 (서비스)");
            return HeadDto.toHeadDto(find.get());
        }
    }

    public HeadDto updateHead(HeadDto headDto) {
        HeadEntity afterEntity = headRepository.save(HeadEntity.toHeadEntity(headDto));
        return HeadDto.toHeadDto(afterEntity);
    }

    public DetailDto updateSelectDetail(Long detailId) {
        Optional<DetailEntity> findDetail = detailRepository.findById(detailId);
        if (findDetail.isPresent()) {
            log.info(findDetail.get().getTitle() + "의 내용을 찾음 (서비스)");
            return DetailDto.toDetailDto(findDetail.get());
        } else {
            log.info("찾은 결과가 없음 (서비스)");
            return null;
        }
    }

    public DetailDto updateDetail(DetailDto detailDto) {
        DetailEntity afterEntity = DetailEntity.toDetailEntity(detailDto);
        detailRepository.save(afterEntity);
        return DetailDto.toDetailDto(afterEntity);
    }

    public WorkDto updateSelectWork(Long workId) {
        Optional<WorkEntity> find = workRepository.findById(workId);
        if (find.isEmpty()) {
            return null;
        } else {
            return WorkDto.toWorkDto(find.get());
        }
    }
    public WorkDto updateWork(WorkDto workDto) {
        WorkEntity afterEntity = WorkEntity.toWorkEntity(workDto);
        workRepository.save(afterEntity);
        return WorkDto.toWorkDto(afterEntity);
    }

    public HeadDto editHead(String title, String startDate, String deadline, String discription, Long headId) {
        Optional<HeadEntity> headEntity = headRepository.findById(headId);
        if (headEntity.isPresent()) {
            log.info("");
            Date startDay = dateManager.formatter(startDate);
            Date endDay = dateManager.formatter(deadline);
            HeadDto headDto = HeadDto.toHeadDto(headEntity.get());
            headDto.setTitle(title);
            headDto.setStartDay(startDay);
            headDto.setEndDay(endDay);
            headDto.setDiscription(discription);
            HeadDto editHeadDto = HeadDto.toHeadDto(headRepository.save(HeadEntity.toHeadEntity(headDto)));
            return editHeadDto;
        } else return null;
    }

    public DetailDto editDetail(String title, String startDate, String deadline, String discription,
                                Long headId, Long detailId) {
        Optional<DetailEntity> detailEntity = detailRepository.findById(detailId);
        if (detailEntity.isPresent()) {
            Date startDay = dateManager.formatter(startDate);
            Date endDay = dateManager.formatter(deadline);
            DetailDto detailDto = DetailDto.toDetailDto(detailEntity.get());
            detailDto.setTitle(title);
            detailDto.setStartDay(startDay);
            detailDto.setEndDay(endDay);
            detailDto.setDiscription(discription);
            detailDto.setHeadIdToDetail(headRepository.findById(headId).orElse(null));
            DetailDto editDetailDto = DetailDto.toDetailDto(detailRepository.save(DetailEntity.toDetailEntity(detailDto)));
            return editDetailDto;
        } return null;
    }

    /* - - - - 수정 메서드 끝 - - - - - */

    /* - - - - 삭제 메서드 시작 - - - - - */
    public boolean deleteHead(Long id) {
        headRepository.deleteById(id);
        if (headRepository.findById(id).isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteDetail(Long id) {
        detailRepository.deleteById(id);
        if (detailRepository.findById(id).isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean deleteWork(Long id) {
        workRepository.deleteById(id);
        if (workRepository.findById(id).isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    /* - - - - 삭제 메서드 끝 - - - - - */

    /* - - - - 상태 변경 메서드 - - - -  */
    // work 상태 변경 메서드
    public WorkDto workCompletionChange(WorkDto workDto) {
        log.info("workCompletionChange 호출");
        WorkDto changeWorkDto;
        if(workDto.getCompletion() == 0) {
            workDto.setCompletion(1);
            changeWorkDto = updateWork(workDto);
            log.info("완료 상태로 변경");
            checkWorkCompletion(workDto);
            return changeWorkDto;
        } else if(workDto.getCompletion() == 1) {
            workDto.setCompletion(0);
            changeWorkDto = updateWork(workDto);
            log.info("미완료 상태로 변경");
            checkWorkCompletion(workDto);
            return changeWorkDto;
        }
        // work 상태를 검사하여 상위 detail 상태 자동 수정
        return null;
    }
    public DetailDto detailCompletionChange(DetailDto detailDto) {
        DetailDto changeDetailDto;
        if (detailDto.getCompletion() == 0) {
            detailDto.setCompletion(1);
            changeDetailDto = updateDetail(detailDto);
            checkDetailCompletion(detailDto);
            return changeDetailDto;
        } else if(detailDto.getCompletion() == 1) {
            detailDto.setCompletion(0);
            changeDetailDto = updateDetail(detailDto);
            checkDetailCompletion(detailDto);
            return changeDetailDto;
        }
        return null;
    }

    public HeadDto headCompletionChange(HeadDto headDto) {
        HeadDto changeHeadDto;
        if (headDto.getCompletion() == 0) {
            headDto.setCompletion(1);
            changeHeadDto = updateHead(headDto);
            return changeHeadDto;
        } else if(headDto.getCompletion() == 1) {
            headDto.setCompletion(0);
            changeHeadDto = updateHead(headDto);
            return changeHeadDto;
        }
        return null;
    }

    // work 모두 완료시, 자동 완료. 미완료시 자동 미완료 처리
    public void checkWorkCompletion(WorkDto workDto) {
        log.info("checkWorkCompletion() 실행");
        Optional<DetailEntity> detailEntity = detailRepository.findById(workDto.getDetailIdToWork().getDetailId());
        log.info("상위 detail 확인 완료. title : " + detailEntity.get().getTitle());
        List<WorkEntity> workEntityList = workRepository.findAllByDetailIdToWork_DetailId(detailEntity.get().getDetailId());
        boolean allComplete = true;
        for (WorkEntity workEntity : workEntityList) {
            if(workEntity.getCompletion() == 0) {
                log.info("미완 상태의 work 발견");
                allComplete = false;
            }
        }

        DetailDto detailDto = DetailDto.toDetailDto(detailEntity.get());

        if (allComplete) {
            log.info("모든 work 완료 확인됨");
            detailDto.setCompletion(1);
            updateDetail(detailDto);
            log.info("상위 detail 완료 상태로 자동 수정");
            checkDetailCompletion(detailDto);
        } else if (!allComplete) {
            log.info("미완 상태의 work 확인됨");
            detailDto.setCompletion(0);
            log.info("상위 detail 미완 상태로 자동 수정");
            updateDetail(detailDto);
        }
    }

    public void checkDetailCompletion(DetailDto detailDto) {
        log.info("detail 상태 확인을 위한 checkDetailCompletion() 실행");
        Optional<HeadEntity> headEntity = headRepository.findById(detailDto.getHeadIdToDetail().getHeadId());
        log.info("상위 head 확인 완료. title : " + headEntity.get().getTitle());
        List<DetailEntity> detailEntityList = detailRepository.findAllByHeadIdToDetail_HeadId(headEntity.get().getHeadId());
        boolean allComplete = true;
        for (DetailEntity detailEntity : detailEntityList) {
            if(detailEntity.getCompletion() == 0) {
                log.info("미완 상태의 head 발견");
                allComplete = false;
            }
        }

        HeadDto headDto = HeadDto.toHeadDto(headEntity.get());

        if(allComplete) {
            log.info("모든 detail 완료 확인됨");
            headDto.setCompletion(1);
            updateHead(headDto);
            log.info("상위 head 완료 상태로 자동 수정");
        } else if (!allComplete) {
            log.info("미완 상태의 detail 식별됨");
            headDto.setCompletion(0);
            updateHead(headDto);
            log.info("상위 head 미완 상태로 자동 수정");
        }
    }

    /* - - - - 댓글 기능 - - - - - */

    //댓글 기능 (댓글리스트 불러오기)
    public List<WorkCommentDto> findByComment(Long workId) {
        List<WorkCommentEntity> entityList = workCommentRepository.findAllByWorkIdToComment_WorkId(workId);
        if (entityList.isEmpty()) {
            log.info("해당 문서에 댓글 없음 (서비스)");
            List<WorkCommentDto> commentDtoList = new ArrayList<>();
            return commentDtoList;
        } else {
            List<WorkCommentDto> commentDtoList = new ArrayList<>();
            for (WorkCommentEntity commentEntity : entityList) {
                commentDtoList.add(WorkCommentDto.toWorkCommentDto(commentEntity));
            }
            log.info("댓글 리스트 불러오기 성공(서비스)");
            return commentDtoList;
        }
    }
    //댓글기능 (댓글 추가)
    public List<WorkCommentDto> plusComment(WorkCommentDto workCommentDto, Long workId) {
        if (workCommentDto.equals(null)) {
            log.info("코멘트가 비어있음 (서비스)");
            return null;
        } else {
            workCommentRepository.save(WorkCommentDto.toWorkCommentEntity(workCommentDto));
            return findByComment(workId);
        }
    }

    //update를 위한 Comment Find
    public WorkCommentDto findComment(Long commentId){
        Optional<WorkCommentEntity> documentCommentEntity=  workCommentRepository.findById(commentId);
        return WorkCommentDto.toWorkCommentDto(documentCommentEntity.get());
    }


    //댓글 삭제
    public List<WorkCommentDto> deleteComment(Long commentId, Long workId){
        Optional<WorkCommentEntity> now = workCommentRepository.findById(commentId);
        WorkCommentDto workCommentDto = WorkCommentDto.toWorkCommentDto(now.get());
        workCommentRepository.deleteById(commentId);
        return findByComment(workId);
    }


    /* - - - - 댓글 기능 끝- - - - - */
}

