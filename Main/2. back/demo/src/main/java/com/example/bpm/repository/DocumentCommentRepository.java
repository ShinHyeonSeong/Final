package com.example.bpm.repository;

import com.example.bpm.entity.DocumentCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentCommentRepository extends JpaRepository<DocumentCommentEntity, Long> {

    List<DocumentCommentEntity> findAllByDocumentIdToComment_DocumentId(String documentId);

}
