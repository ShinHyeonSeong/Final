package com.example.bpm.repository;

import com.example.bpm.entity.FriendEntity;
import com.example.bpm.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<FriendEntity, String> {

    @Query(value = // 괄호한 value 값의 sql문을 실행시킴
            "SELECT * " +
                    "FROM friend " +
                    "WHERE id1 = :id1",
            nativeQuery = true)
    //sql문이 작동하게 도와줌
    List<FriendEntity> findById1(String id1);
}
