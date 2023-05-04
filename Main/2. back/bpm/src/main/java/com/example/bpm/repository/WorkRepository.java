package com.example.bpm.repository;

import com.example.bpm.entity.WorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository  extends JpaRepository<WorkEntity, Long> {
}
