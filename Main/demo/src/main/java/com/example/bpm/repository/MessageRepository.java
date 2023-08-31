package com.example.bpm.repository;

import com.example.bpm.entity.message.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findAllByUserIdToMessageRecv_Uuid(String recvUserUuid);

    List<MessageEntity> findAllByUserIdToMessageSend_Uuid(String sendUserUuid);

    public List<MessageEntity> findAllByProjectIdToMessage_ProjectId(Long projectId);
    public void deleteAllByProjectIdToMessage_ProjectId(Long projectId);

}
