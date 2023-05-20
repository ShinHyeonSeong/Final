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
    @Column(name = "document_id")
    private String documentId;

    @Column(name = "title")
    private String title;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "date_document")
    private String dateDocument;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "documentIdToWorkDocument")
    private List<WorkDocumentEntity> workDocumentEntityList  = new ArrayList<>();
}
