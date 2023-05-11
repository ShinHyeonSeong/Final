package com.example.bpm.dto;

import com.example.bpm.entity.UserEntity;
import com.example.bpm.entity.WorkEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWorkDto {
    private WorkEntity workIdToUserWork;

    private UserEntity userIdToUserWork;

}
