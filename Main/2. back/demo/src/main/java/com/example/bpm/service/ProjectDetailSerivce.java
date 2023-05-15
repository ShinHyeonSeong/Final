package com.example.bpm.service;

import com.example.bpm.dto.*;
import com.example.bpm.entity.*;
import com.example.bpm.repository.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    Date currentDate = new Date(); // 시작 날짜(현재) 생성

    // 마감기한 문자열 Date 타입 반환 메서드. 문자열 형식 상이로 변환 실패시 null
    public Date formatter(String dateString) {
        Date date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(dateString);
        } catch (ParseException ex) {
            return null;
        }
        return date;
    }

    /* - - - - 생성 메서드 시작 - - - - */

    //목표 만들기 -> projectId는 Session으로 적용시켜 값을 받아와야함
    public HeadDto createHead(String title, String deadline, String discription, ProjectDto projectDto) {
        if (headRepository.findByTitle(title).isPresent()) {
            log.info("head title 이 이미 존재한다. (서비스)");
            return null;
        } else {
            log.info("createHead service 작동");
            Date endDay = formatter(deadline);
            if (endDay == null) return null;

            HeadDto createHeadDto = new HeadDto();
            createHeadDto.setTitle(title);
            createHeadDto.setDiscription(discription);
            createHeadDto.setStartDay(currentDate);
            createHeadDto.setEndDay(endDay);
            createHeadDto.setCompletion(0);
            createHeadDto.setProjectIdToHead(projectRepository.findById(projectDto.getProjectId()).orElse(null));
            log.info("HeadDto 생성 완료");
            HeadEntity createHead = headRepository.save(HeadEntity.toHeadEntity(createHeadDto));
            log.info("HeadEntity 저장 및 생성 완료");
            return HeadDto.toHeadDto(createHead);
        }
    }

    //작업 만들기 (컨트롤러에서 연결할 목표를 찾아서 매개변수 값으로 넣어줘야함)
    public DetailDto createDetail(String title, String deadline, String discription,
                                  HeadDto connectedHead, ProjectDto projectDto) {
        if (detailRepository.findByTitle(title).isPresent()) {
            log.info("detail title 이 이미 존재한다. (서비스)");
            return null;
        } else {
            log.info("createDetail service 작동");
            Date endDay = formatter(deadline);
            if (endDay == null) return null;

            DetailDto createDetailDto = new DetailDto();
            createDetailDto.setTitle(title);
            createDetailDto.setDiscription(discription);
            createDetailDto.setStartDay(currentDate);
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
    public WorkDto createWork(String title, String discription, String deadline,
                              DetailDto connectDetail, ProjectDto projectDto) {
        if (workRepository.findByTitle(title).isPresent()) {
            log.info("work title 이 이미 존재한다. (서비스)");
            return null;
        } else {
            Date endDay = formatter(deadline);
            if (endDay == null) return null;

            WorkDto createWorkDto = new WorkDto();
            createWorkDto.setTitle(title);
            createWorkDto.setDiscription(discription);
            createWorkDto.setStartDay(currentDate);
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

    public List<WorkDto> selectAllWorkForUser(UserDto userDto) {
        List<UserWorkEntity> userWorkList = userWorkRepository.findAllByUserIdToUserWork_Uuid(userDto.getUuid());
        List<WorkDto> workDtoList = new ArrayList<>();
        for(UserWorkEntity userWorkEntity : userWorkList) {
            workDtoList.add(WorkDto.toWorkDto(workRepository.findById(
                    userWorkEntity.getWorkIdToUserWork().getWorkId()).orElse(null)));
        }
        return workDtoList;
    }
//
//    public List<WorkDto> selectAllWorkForProject(ProjectDto projectDto) {
//        workRepository
//    }
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
        HeadEntity afterEntity = HeadEntity.toHeadEntity(headDto);
        headRepository.save(afterEntity);
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


}

