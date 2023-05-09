package com.example.bpm.dto;

import com.example.bpm.entity.DetailEntity;
import com.example.bpm.entity.ProjectEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class WorkDto {
    private Long workId;

    private String title;

    private Date startDay;

    private Date endDay;

    private int completion;

    private DetailEntity detailIdToWork;

    private ProjectEntity projectIdToWork;

}
