package com.example.bpm.repository;

import com.example.bpm.entity.FriendEntity;
import com.example.bpm.entity.FriendRequestEntity;
import com.example.bpm.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequestEntity, String> {

    @Query(value = // 괄호한 value 값의 sql문을 실행시킴
            "insert into  " +
                    "friendRequst " +
                    "values (sendUUID, recvUUID)",
            nativeQuery = true)
        //sql문이 작동하게 도와줌
    FriendRequestEntity plusFriend(String sendUUID, String recvUUID);


    @Query(value = "delete from " + "friendRequst" + "where sendUUID = sendId and recvUUID = recvId", nativeQuery = true)
    void deleteRow(String sendId, String recvId);

    @Query(value = "select * " +
            "from friendReust " +
            "where sendUUID = sendId AND recvUUID = recvId", nativeQuery = true)
    FriendRequestEntity findRequestRow(String sendId, String recvId);


}

