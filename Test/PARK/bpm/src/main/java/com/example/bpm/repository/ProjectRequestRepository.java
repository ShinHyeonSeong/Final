package com.example.bpm.repository;

import com.example.bpm.entity.ProjectRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRequestRepository extends JpaRepository<ProjectRequestEntity, Long> {

    @Query(value = // 괄호한 value 값의 sql문을 실행시킴
            "insert into  " +
                    "projectRequst " +
                    "values (sendUUID, recvUUID)",
            nativeQuery = true)
        //sql문이 작동하게 도와줌
    ProjectRequestEntity plusProject(String sendUUID, String recvUUID);


    @Query(value = "delete from " + "friendRequst" + "where sendUUID = sendId and recvUUID = recvId", nativeQuery = true)
    void deleteRow(String sendId, String recvId);

    @Query(value = "select * " +
            "from friendReust " +
            "where sendUUID = sendId AND recvUUID = recvId", nativeQuery = true)
    ProjectRequestEntity findRequestRow(String sendId, String recvId);


}

