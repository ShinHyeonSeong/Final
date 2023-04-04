package com.example.bpm.repository;

import com.example.bpm.entity.ProjectRequestEntity;
import com.example.bpm.entity.ProjectRequestPKEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//여기서 오류가 뜰 수도 있는데 제네릭 선언 할때 <연관된 Entity, 데이터타입> 여기에 DB의 데이터 타입인지 Entity 데이터 타입인지 모르겠어 하고 알려줘
//기본키가 하나일 때는 기본키의 타입을 넣어주면 되지만 복합키 같은 경우는 식별자 클래스를 기본키가 들어가야 되는 부분에 넣어주시면 됩니다.
public interface ProjectRequestRepository extends JpaRepository<ProjectRequestEntity, ProjectRequestPKEntity> {
    @Query(value = // 괄호한 value 값의 sql문을 실행시킴
            "insert into  " +
                    "projectRequst " +
                    "values (sendUUID, recvUUID)",
            nativeQuery = true)
        //sql문이 작동하게 도와줌
    ProjectRequestEntity plusProject(String sendUUID, String recvUUID);

    ProjectRequestEntity selectById(String sendUUID, String recvUUID);
}
