package com.example.bpm.dto;

import com.example.bpm.entity.Document;
import com.example.bpm.entity.DocumentCommentEntity;
import com.example.bpm.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentCommentDto {
    private Long documentCommentId;

    private String comment;

    private Document DocumentIdToComment;

    private UserEntity userIdToComment;

    public static DocumentCommentDto toDocumentCommentDto(DocumentCommentEntity documentCommentEntity){
        DocumentCommentDto documentCommentDto = new DocumentCommentDto();
        documentCommentDto.setDocumentCommentId(documentCommentEntity.getDocumentCommentId());
        documentCommentDto.setComment(documentCommentEntity.getComment());
        documentCommentDto.setDocumentIdToComment(documentCommentEntity.getDocumentIdToComment());
        documentCommentDto.setUserIdToComment(documentCommentEntity.getUserIdToComment());
        return documentCommentDto;
    }

}
