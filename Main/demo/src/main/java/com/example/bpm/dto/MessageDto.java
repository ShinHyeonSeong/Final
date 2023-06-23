package com.example.bpm.dto;

import com.example.bpm.entity.MessageEntity;
import com.example.bpm.entity.ProjectEntity;
import com.example.bpm.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Long messageId;

    private String title;

    private String content;

    private UserEntity userIdToMessageSend;

    private ProjectEntity userIdToMessageRecv;

    private ProjectEntity projectIdToMessage;

    public static MessageDto toMessageDto(MessageEntity messageEntity){
        MessageDto messageDto = new MessageDto();
        messageDto.setMessageId(messageEntity.getMessageId());
        messageDto.setTitle(messageEntity.getTitle());
        messageDto.setContent(messageEntity.getContent());
        messageDto.setUserIdToMessageSend(messageEntity.getUserIdToMessageSend());
        messageDto.setUserIdToMessageRecv(getUserIdToMessageRecv());
        messageDto.setProjectIdToMessage(messageEntity.getProjectIdToMessage());
        return messageDto;
    }


}
