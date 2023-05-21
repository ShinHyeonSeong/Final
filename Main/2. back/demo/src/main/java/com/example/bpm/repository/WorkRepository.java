package com.example.bpm.repository;

import com.example.bpm.entity.DetailEntity;
import com.example.bpm.entity.WorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WorkRepository extends JpaRepository<WorkEntity, Long> {
    @Query(value = "select * from work where title = :title", nativeQuery = true)
    Optional<WorkEntity> findByTitle(String title);

    public List<WorkEntity> findAllByProjectIdToWork_ProjectId(Long projectId);

    public List<WorkEntity> findAllByDetailIdToWork_DetailId(Long detailId);
}
