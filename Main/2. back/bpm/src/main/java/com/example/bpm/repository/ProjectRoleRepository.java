package com.example.bpm.repository;

import com.example.bpm.entity.ProjectRoleEntity;
import com.example.bpm.entity.ProjectRolePKEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRoleRepository extends JpaRepository<ProjectRoleEntity, ProjectRolePKEntity> {


    @Query(value = "select * from projectRole " +
            "where" +
            "projectRole.role = :role",
            nativeQuery = true)
    //role의 값에 따라 프로젝트 리스트를 보여주는 메서드
    List<ProjectRoleEntity> selectToRoleList(Long role);

    @Query(value = "insert into ")

}
