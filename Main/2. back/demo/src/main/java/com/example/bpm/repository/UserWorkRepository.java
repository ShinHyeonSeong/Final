package com.example.bpm.repository;

import com.example.bpm.entity.UserEntity;
import com.example.bpm.entity.UserWorkEntity;
import com.example.bpm.entity.UserWorkPKEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWorkRepository extends JpaRepository<UserWorkEntity, UserWorkPKEntity> {
    public List<UserWorkEntity> findAllByUserIdToUserWork_Uuid(String uuid);
}
