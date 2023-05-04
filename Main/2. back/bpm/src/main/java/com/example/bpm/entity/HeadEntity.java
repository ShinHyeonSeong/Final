package com.example.bpm.entity;

import com.example.bpm.dto.HeadDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "head")
public class HeadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "head_id")
    private Long headId;

    @Column(name = "title")
    private String title;

    @Column(name = "start_day")
    private Date startDay;

    @Column(name = "end_day")
    private Date endDay;

    @Column(name = "completion")
    private int completion;

    @JoinColumn(name = "project_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private ProjectEntity projectIdToHead;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "headIdToDetail")
    private List<DetailEntity> detailEntityList = new ArrayList<>();

    public static HeadEntity toHeadEntity(HeadDto headDto) {
        HeadEntity headEntity = new HeadEntity();
        headEntity.setHeadId(headDto.getHeadId());
        headEntity.setTitle(headDto.getTitle());
        headEntity.setStartDay(headDto.getStartDay());
        headEntity.setEndDay(headDto.getEndDay());
        headEntity.setCompletion(headDto.getCompletion());
        headEntity.setProjectIdToHead(headDto.getProjectIdToHead());
        return headEntity;
    }
}
