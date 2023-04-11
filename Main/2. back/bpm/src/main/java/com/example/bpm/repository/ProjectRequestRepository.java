package com.example.bpm.repository;

import com.example.bpm.entity.ProjectRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

//send와 recv로 id를 식별하고 get() 함수로 projectid를 찾는 방식을 써야한다.
//아마 오류 뜰 확률 ㅈㄴ 높음 String이 PK 키인데 Long 타입이 껴있음....
public interface ProjectRequestRepository extends JpaRepository<ProjectRequestEntity, String> {

    void plusProjectRequest(String sendUser, String recvUser, Long projectId);

    Optional<ProjectRequestEntity> selectToRequsetRow(String sendUser, String recvUser, Long projectId);

    List<ProjectRequestEntity> ToParticipantsList(String recvUUID);

    void deleteToRequestRow(String sendUUID, String recvUUID, Long projectId);
}
