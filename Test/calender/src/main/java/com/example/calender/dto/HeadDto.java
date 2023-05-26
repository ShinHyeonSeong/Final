package com.example.calender.dto;

import com.example.calender.entity.HeadEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class HeadDto {
    private Long headId;

    private String title;

    private Date startDay;

    private Date endDay;

    public static HeadEntity toHeadEntity(HeadDto headDto) {
        HeadEntity headEntity = new HeadEntity();
        headEntity.setHeadId(headDto.getHeadId());
        headEntity.setTitle(headDto.getTitle());
        headEntity.setStartDay(headDto.getStartDay());
        headEntity.setEndDay(headDto.getEndDay());
        return headEntity;
    }

    public static HeadDto toHeadDto(HeadEntity headEntity) {
        HeadDto headDto = new HeadDto();
        headDto.setHeadId(headEntity.getHeadId());
        headDto.setTitle(headEntity.getTitle());
        headDto.setStartDay(headEntity.getStartDay());
        headDto.setEndDay(headEntity.getEndDay());
        return headDto;
    }
}
