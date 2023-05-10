package com.example.bpm.repository;

import com.example.bpm.entity.ProjectRoleEntity;
import com.example.bpm.entity.ProjectRolePKEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRoleRepository extends JpaRepository<ProjectRoleEntity, ProjectRolePKEntity> {

    @Query(value = "select * from project_role where uuid = :uuid", nativeQuery = true)
    List<ProjectRoleEntity> userForRole(String uuid);

    @Query(value = "select * from project_role where project_id = :id", nativeQuery = true)
    List<ProjectRoleEntity> userForProject(Long id);

    //public List<ProjectRoleEntity> findAllByUuidInRole(String uuid);

    @Query(value = "insert into project_requst(project_id, uuid, role)" +
            "values (?,?,?)", nativeQuery = true)
    ProjectRoleEntity insertToRoleEntity(Long projectId, String recvUUID, Long aLong);
}