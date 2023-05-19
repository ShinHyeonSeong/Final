package com.codingrecipe.board.repository;

import com.codingrecipe.board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

@Repository
@Table(name = "comment")
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query(value = "select * from comment where documentId = :documentId", nativeQuery = true)
    List<CommentEntity> findCommentEntitiesByUUID(Long documentId);
}
