package com.example.bpm.service;

import com.example.bpm.dto.DetailDto;
import com.example.bpm.dto.HeadDto;
import com.example.bpm.dto.WorkDto;
import com.example.bpm.entity.DetailEntity;
import com.example.bpm.entity.HeadEntity;
import com.example.bpm.entity.ProjectEntity;
import com.example.bpm.entity.WorkEntity;
import com.example.bpm.repository.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

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
    @Autowired
    private final DetailRepository detailRepository;

    /* - - - - 생성 메서드 시작 - - - - */

    //목표 만들기 -> projectId는 Session으로 적용시켜 값을 받아와야함
    public HeadDto createHead(HeadDto headDto) {
        if (headRepository.findByTitle(headDto.getTitle()).isPresent()) {
            log.info("head title 이 이미 존재한다. (서비스)");
            return null;
        } else {
            log.info("head 생성 성공 (서비스)");
            HeadEntity createHead = HeadEntity.toHeadEntity(headDto);
            headRepository.save(createHead);
            return HeadDto.toHeadDto(createHead);
        }
    }

    //작업 만들기 (컨트롤러에서 연결할 목표를 찾아서 매개변수 값으로 넣어줘야함)
    public DetailDto createDetail(DetailDto detailDto) {
        if (detailRepository.findByTitle(detailDto.getTitle()).isPresent()) {
            log.info("detail title 이 이미 존재한다. (서비스)");
            return null;
        } else {
            log.info("detail 생성 성공 (서비스)");
            DetailEntity createDetail = DetailEntity.toDetailEntity(detailDto);
            detailRepository.save(createDetail);
            return DetailDto.toDetailDto(createDetail);
        }
    }

    //내 작업 만들기
    public WorkDto createWork(WorkDto workDto) {
        if (workRepository.findByTitle(workDto.getTitle()).isPresent()) {
            log.info("work title 이 이미 존재한다. (서비스)");
            return null;
        } else {
            log.info("work 생성 성공 (서비스)");
            WorkEntity createWork = WorkEntity.toWorkEntity(workDto);
            workRepository.save(createWork);
            return WorkDto.toWorkDto(createWork);
        }
    }

    /* - - - - 생성 메서드 끝 - - - - */

    /* - - - - 선택 메서드 시작 - - - - - */
    public HeadDto selectHead(Long id){
        Optional<HeadEntity> find = headRepository.findById(id);
        return HeadDto.toHeadDto(find.get());
    }
    public DetailDto selectDetail(Long id){
        Optional<DetailEntity> find = detailRepository.findById(id);
        return DetailDto.toDetailDto(find.get());
    }
    public WorkDto selectWork(Long id){
        Optional<WorkEntity> find = workRepository.findById(id);
        return WorkDto.toWorkDto(find.get());
    }
    /* - - - - 선택 메서드 끝 - - - - - */

    /* - - - - 수정 메서드 시작 - - - - - */
    public HeadDto updateHead(HeadDto headDto) {
        HeadEntity afterEntity = HeadEntity.toHeadEntity(headDto);
        headRepository.save(afterEntity);
        return HeadDto.toHeadDto(afterEntity);
    }

    public DetailDto updateDetail(DetailDto detailDto) {
        DetailEntity afterEntity = DetailEntity.toDetailEntity(detailDto);
        detailRepository.save(afterEntity);
        return DetailDto.toDetailDto(afterEntity);
    }

    public WorkDto updateWork(WorkDto workDto) {
        WorkEntity afterEntity = WorkEntity.toWorkEntity(workDto);
        workRepository.save(afterEntity);
        return WorkDto.toWorkDto(afterEntity);
    }

    /* - - - - 수정 메서드 끝 - - - - - */

    /* - - - - 삭제 메서드 시작 - - - - - */
    public boolean deleteHead(Long id){
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

