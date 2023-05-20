package com.example.bpm.entity;

import javax.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Document implements Serializable {

    @Id
    @Column(name = "documentId")
    private String documentId;

    @Column(name = "title")
    private String title;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "userName")
    private String userName;

    @Column(name = "dateDocument")
    private String dateDocument;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "DocumentIdToComment")
    private List<DocumentCommentEntity> documentCommentEntities  = new ArrayList<>();

}
