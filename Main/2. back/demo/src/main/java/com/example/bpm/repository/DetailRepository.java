package com.example.bpm.repository;

import com.example.bpm.entity.DetailEntity;
import com.example.bpm.entity.HeadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailRepository extends JpaRepository<DetailEntity, Long> {

}
