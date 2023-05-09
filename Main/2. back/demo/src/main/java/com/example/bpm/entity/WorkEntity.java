package com.example.bpm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "work")
public class WorkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_id")
    private Long workId;

    @Column(name = "title")
    private String title;

    @Column(name = "start_day")
    private Date startDay;

    @Column(name = "end_day")
    private Date endDay;

    @Column(name = "completion")
    private int completion;

    @JoinColumn(name = "detail_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private DetailEntity detailIdToWork;

    @JoinColumn(name = "project_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private ProjectEntity projectIdToWork;




}
