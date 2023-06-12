package com.example.bpm.service;

import com.example.bpm.dto.HeadDto;
import com.example.bpm.dto.ProjectDto;
import com.example.bpm.entity.DetailEntity;
import com.example.bpm.entity.HeadEntity;
import com.example.bpm.repository.DetailRepository;
import com.example.bpm.repository.HeadRepository;
import com.example.bpm.service.dateLogic.DateManager;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@NoArgsConstructor
@Slf4j
public class ExceptionService {
    @Autowired
    HeadRepository headRepository;
    @Autowired
    DetailRepository detailRepository;

    DateManager dateManager = new DateManager();

    public String headErrorCheck(ProjectDto projectDto, String title, String startDay, String endDay) {
        if(headRepository.findByTitle(title).isPresent()) {
            return "이미 존재하는 목표 제목입니다.";
        }

        Date projectStartDate = projectDto.getStartDay();
        Date projectEndDate = projectDto.getEndDay();

        Date headStartDate = dateManager.formatter(startDay);
        Date headEndDate = dateManager.formatter(endDay);

        int startDateResult = headStartDate.compareTo(projectStartDate);
        int endDateResult = headEndDate.compareTo(projectEndDate);

        if(startDateResult < 0) {
            return "프로젝트 시작기한보다 빠릅니다.";
        }
        if(endDateResult > 0) {
            return "프로젝트 마감기한을 초과했습니다.";
        }
        return null;
    }

    public String detailErrorCheck(String title, String startDay, String endDay, Long headId) {
        HeadDto headDto = HeadDto.toHeadDto((headRepository.findById(headId)).orElse(null));
        if (detailRepository.findByTitle(title).isPresent()) {
            return "이미 존재하는 하위 목표 제목입니다.";
        }

        Date headStartDate = headDto.getStartDay();
        Date headEndDate = headDto.getEndDay();

        Date detailStartDate = dateManager.formatter(startDay);
        Date detailEndDate = dateManager.formatter(endDay);

        int startDateResult = detailStartDate.compareTo(headStartDate);
        int endDateResult = detailEndDate.compareTo(headEndDate);

        if(startDateResult < 0) {
            return "상위 목표의 시작기한보다 빠릅니다.";
        }
        if(endDateResult > 0) {
            return "상위 목표의 마감기한을 초과했습니다.";
        }
        return null;
    }
}
