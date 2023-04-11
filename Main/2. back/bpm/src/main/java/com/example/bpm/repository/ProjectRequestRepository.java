package com.example.bpm.repository;

import com.example.bpm.entity.ProjectRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//send와 recv로 id를 식별하고 get() 함수로 projectid를 찾는 방식을 써야한다.
public interface ProjectRequestRepository extends JpaRepository<ProjectRequestEntity, String> {

}
