package com.codingrecipe.board.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "document")
public class DocumentEntity {
    @Id
    @Column(name = "document_id")
    private Long documentId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "documentIdToReply")
    private List<CommentEntity> documentToReply = new ArrayList<>();

}
