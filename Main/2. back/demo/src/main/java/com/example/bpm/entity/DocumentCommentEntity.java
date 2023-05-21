package com.example.bpm.entity;

import com.example.bpm.dto.DocumentCommentDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "document_comment")
public class DocumentCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_comment_id")
    private Long documentCommentId;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "documentId")
    private Document documentIdToComment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid")
    private UserEntity userIdToComment;

    public static DocumentCommentEntity toDocumentCommentEntity(DocumentCommentDto documentCommentDto) {
        DocumentCommentEntity documentCommentEntity = new DocumentCommentEntity();
        documentCommentEntity.setDocumentCommentId(documentCommentDto.getDocumentCommentId());
        documentCommentEntity.setComment(documentCommentDto.getComment());
        documentCommentEntity.setDocumentIdToComment(documentCommentDto.getDocumentIdToComment());
        documentCommentEntity.setUserIdToComment(documentCommentDto.getUserIdToComment());
        return documentCommentEntity;
    }


}
