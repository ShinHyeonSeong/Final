package com.example.bpm.dto.project;

import com.example.bpm.entity.project.data.DetailEntity;
import com.example.bpm.entity.project.data.ProjectEntity;
import com.example.bpm.entity.project.data.WorkEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class WorkDto {
    private Long workId;

    private String title;

    private String discription;

    private Date startDay;

    private Date endDay;

    private int completion;

    private DetailEntity detailIdToWork;

    private ProjectEntity projectIdToWork;

    public WorkEntity toEntity(){
        WorkEntity workEntity = new WorkEntity();

        workEntity.setWorkId(workId);
        workEntity.setTitle(title);
        workEntity.setDiscription(discription);
        workEntity.setStartDay(startDay);
        workEntity.setEndDay(endDay);
        workEntity.setCompletion(completion);
        workEntity.setDetailIdToWork(detailIdToWork);
        workEntity.setProjectIdToWork(projectIdToWork);

        return workEntity;
    }

    public void insertEntity(WorkEntity workEntity){
        this.workId = workEntity.getWorkId();
        this.title = workEntity.getTitle();
        this.discription = workEntity.getDiscription();
        this.startDay = workEntity.getStartDay();
        this.endDay = workEntity.getEndDay();
        this.completion = workEntity.getCompletion();
        this.detailIdToWork = workEntity.getDetailIdToWork();
        this.projectIdToWork = workEntity.getProjectIdToWork();
    }
}
