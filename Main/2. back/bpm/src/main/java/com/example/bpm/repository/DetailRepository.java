package com.example.bpm.repository;

import com.example.bpm.entity.DetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailRepository extends JpaRepository<DetailEntity, Long> {
}
