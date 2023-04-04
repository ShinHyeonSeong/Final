package com.example.bpm.repository;

import com.example.bpm.entity.ProjectRequestEntity;
import com.example.bpm.entity.ProjectRequestPKEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

//여기서 오류가 뜰 수도 있는데 제네릭 선언 할때 <연관된 Entity, 데이터타입> 여기에 DB의 데이터 타입인지 Entity 데이터 타입인지 모르겠어 하고 알려줘
//기본키가 하나일 때는 기본키의 타입을 넣어주면 되지만 복합키 같은 경우는 식별자 클래스를 기본키가 들어가야 되는 부분에 넣어주시면 됩니다.
public interface ProjectRequestRepository extends JpaRepository<ProjectRequestEntity, ProjectRequestPKEntity> {
    @Query(// 괄호한 value 값의 sql문을 실행시킴
            value = "insert into  " +
                    "projectRequst " +
                    "values (:sendUUID, :recvUUID, :projectId)", //파라미터의 변수를 활용하고 싶으면 : 를 붙여라 / ?1 이렇게 하면 1번째 파라미터를 쓰겠다는 의미
            nativeQuery = true)
        //sql문이 작동하게 도와줌
    Optional<ProjectRequestEntity> plusProjectRequest(String sendUUID, String recvUUID, String projectId);

    //특정 Request만 선택하는 메서드
    @Query(value = "select * from projectRequst where" +
            "projectRequst.sendUUID =:sendUUID " +
            "AND projectRequst.recvUUID = :recvUUID" +
            "AND projectRequst.projectID =: proejctId"
            ,nativeQuery = true)
    Optional<ProjectRequestEntity> selectToRequsetRow(String sendUUID, String recvUUID, String projectId);


    //권한자 프로젝트를 확인할 수 있는 메서드
    @Query(value = "select * from projectRequst " +
            "where" +
            "projectRequst.sendUUID = :sendUUID",
            nativeQuery = true)
    List<ProjectRequestEntity> selectToListHostRow(String sendUUID);


    //비권한자 프로젝트를 확인할 수 있는 메서드
    @Query(value = "selct * from projectRequst where" + "proejctRequst.rectUUID =:recvUUID",
            nativeQuery = true)
    ProjectRequestEntity selectTopParticipantsRow(String recvUUID);

}
