package com.example.bpm.repository;

import com.example.bpm.entity.ProjectRoleEntity;
import com.example.bpm.entity.ProjectRolePKEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRoleRepository extends JpaRepository<ProjectRoleEntity, String> {

List<ProjectRoleEntity> findAllById(String uuid);
}
