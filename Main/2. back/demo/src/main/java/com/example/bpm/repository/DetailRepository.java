package com.example.bpm.repository;

import com.example.bpm.entity.DetailEntity;
import com.example.bpm.entity.HeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DetailRepository extends JpaRepository<DetailEntity, Long> {
    @Query(value = "select * from detail where title = :title", nativeQuery = true)
    Optional<DetailEntity> findByTitle(String title);

}
