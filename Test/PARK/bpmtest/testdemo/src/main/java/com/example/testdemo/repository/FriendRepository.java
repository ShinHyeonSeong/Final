package com.example.testdemo.repository;

import com.example.testdemo.entity.AccountEntity;
import com.example.testdemo.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<FriendEntity, Long> {

    @Query(value = // 괄호한 value 값의 sql문을 실행시킴
            "SELECT * " +
                    "FROM friend " +
                    "WHERE id1 = :id1",
            nativeQuery = true )//sql문이 작동하게 도와줌
    List<FriendEntity> findById1(Long id1);
}
