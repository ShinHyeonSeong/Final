package com.example.bpm.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "detail")
public class DetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long detailId;

    @Column(name = "title")
    private String title;

    @Column(name = "start_day")
    private Date startDay;

    @Column(name = "end_day")
    private Date endDay;

    @Column(name = "completion")
    private int completion;

    @JoinColumn(name = "head_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private HeadEntity headIdToDetail;

    @JoinColumn(name = "project_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private ProjectEntity projectIdToDetail;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "detailIdToWork")
    private List<WorkEntity> workEntityList = new ArrayList<>();


}
