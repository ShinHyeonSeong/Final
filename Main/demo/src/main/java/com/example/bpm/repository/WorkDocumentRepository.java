package com.example.bpm.repository;

import com.example.bpm.entity.WorkDocumentEntity;
import com.example.bpm.entity.WorkDocumentPKEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkDocumentRepository extends JpaRepository<WorkDocumentEntity, WorkDocumentPKEntity> {

    public List<WorkDocumentEntity> findAllByWorkIdToWorkDocument_WorkId(Long id);
    public WorkDocumentEntity findByDocumentIdToWorkDocument_DocumentId(String id);

    public void deleteAllByWorkIdToWorkDocument_WorkId(Long id);
}
