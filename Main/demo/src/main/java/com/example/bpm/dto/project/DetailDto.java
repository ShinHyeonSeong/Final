package com.example.bpm.dto.project;

import com.example.bpm.entity.project.data.DetailEntity;
import com.example.bpm.entity.project.data.HeadEntity;
import com.example.bpm.entity.project.data.ProjectEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DetailDto {

    private Long detailId;

    private String title;

    private String discription;

    private Date startDay;

    private Date endDay;

    private int completion;

    private HeadEntity headIdToDetail;

    private ProjectEntity projectIdToDetail;

    public DetailEntity toEntity(){
        DetailEntity detailEntity = new DetailEntity();

        detailEntity.setDetailId(detailId);
        detailEntity.setTitle(title);
        detailEntity.setDiscription(discription);
        detailEntity.setStartDay(startDay);
        detailEntity.setEndDay(endDay);
        detailEntity.setCompletion(completion);
        detailEntity.setHeadIdToDetail(headIdToDetail);
        detailEntity.setProjectIdToDetail(projectIdToDetail);

        return detailEntity;
    }

    public void insertEntity(DetailEntity detailEntity){
        this.detailId = detailEntity.getDetailId();
        this.title = detailEntity.getTitle();
        this.discription = detailEntity.getDiscription();
        this.startDay = detailEntity.getStartDay();
        this.endDay = detailEntity.getEndDay();
        this.completion = detailEntity.getCompletion();
        this.headIdToDetail = detailEntity.getHeadIdToDetail();
        this.projectIdToDetail = detailEntity.getProjectIdToDetail();
    }
}
