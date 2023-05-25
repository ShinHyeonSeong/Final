package com.example.document.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Log {

    @Id
    @Column(name = "logId")
    private String logId;

    @Column(name = "documentId")
    private String documentId;

    @Column(name = "logContent")
    private String log;

    @Column(name = "dateLog")
    private String dateLog;

    @Column(name = "logType")
    private  String logType;
}
