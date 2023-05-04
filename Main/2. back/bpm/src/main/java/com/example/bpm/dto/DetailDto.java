package com.example.bpm.dto;

import com.example.bpm.entity.HeadEntity;
import com.example.bpm.entity.ProjectEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DetailDto {
    private Long detailId;

    private String title;

    private Date startDay;

    private Date endDay;

    private int completion;

    private HeadEntity headIdToDetail;

    private ProjectEntity projectIdToDetail;

}
