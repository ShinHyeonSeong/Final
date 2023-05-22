package com.example.document.repository;

import com.example.document.entity.Document;
import com.example.document.entity.Log;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Table(name = "log")
public interface LogRepository extends JpaRepository<Log, String> {
    List<Log> findByDocumentId(String id);
    Log findBylogId(String id);
}
