package com.codingrecipe.board.repository;

import com.codingrecipe.board.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

@Repository
@Table(name = "document")
public interface DocumentRepository extends JpaRepository<DocumentEntity, String> {
}
