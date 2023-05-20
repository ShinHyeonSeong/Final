package com.codingrecipe.board.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
public class PostEntity {
    @Id
    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "post")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "postIdToReply")
    private List<CommentEntity> postToReply = new ArrayList<>();

}
