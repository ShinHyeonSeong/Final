package com.example.bpm.repository;

import com.example.bpm.entity.ProjectRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

//send와 recv로 id를 식별하고 get() 함수로 projectid를 찾는 방식을 써야한다.
//아마 오류 뜰 확률 ㅈㄴ 높음 String이 PK 키인데 Long 타입이 껴있음....
public interface ProjectRequestRepository extends JpaRepository<ProjectRequestEntity, String> {

    void plusProjectRequest(String send_uuid, String recv_uuid, Long project_id);

    Optional<ProjectRequestEntity> selectToRequsetRow(String send_uuid, String recv_uuid, Long project_id);

    List<ProjectRequestEntity> ToParticipantsList(String recv_uuid);

//    @Query("delete from proej")
    void deleteToRequestRow(String send_uuid, String recv_uuid, Long project_id);
}
