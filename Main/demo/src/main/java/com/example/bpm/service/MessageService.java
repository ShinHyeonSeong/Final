package com.example.bpm.service;

import com.example.bpm.dto.message.MessageDto;
import com.example.bpm.dto.project.ProjectDto;
import com.example.bpm.dto.user.UserDto;
import com.example.bpm.entity.message.MessageEntity;
import com.example.bpm.entity.project.data.ProjectEntity;
import com.example.bpm.entity.user.UserEntity;
import com.example.bpm.repository.MessageRepository;
import com.example.bpm.repository.ProjectRepository;
import com.example.bpm.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@NoArgsConstructor(force = true)
public class MessageService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ProjectRepository projectRepository;
    @Autowired
    private final MessageRepository messageRepository;

    //메세지 보내기
    public void sendMessage(String title, String content, UserDto nowUuid, String recvName, ProjectDto nowProject) {
        Date now = new Date();
        UserEntity recvUser = userRepository.findByName(recvName);

        UserEntity userEntity = nowUuid.toEntity();
        ProjectEntity projectEntity = nowProject.toEntity();

        MessageDto messageDto = new MessageDto(null, title, content, now, userEntity, recvUser, projectEntity);

        messageRepository.save(messageDto.toEntity());
    }

    //수신함 확인 (파라미터 = 현재 로그인된 user, 현재 접속된 project
    public List<MessageDto> selectAllRecv(UserDto recvUser, ProjectDto projectDto) {
        String recvUserUuid = recvUser.getUuid();
        Long projectId = projectDto.getProjectId();
        List<MessageEntity> recvMessageList = messageRepository.findAllByProjectIdToMessage_ProjectIdAndUserIdToMessageRecv_Uuid(projectId ,recvUserUuid);
        List<MessageDto> messageDtos = new ArrayList<>();

        for (MessageEntity messageEntity : recvMessageList) {
            MessageDto messageDto = new MessageDto();
            messageDto.insertEntity(messageEntity);
            messageDtos.add(messageDto);
        }
        return messageDtos;
    }

    //발신함 확인
    public List<MessageDto> selectAllSend(UserDto sendUser, ProjectDto projectDto) {
        String sendUserUuid = sendUser.getUuid();
        Long projectId = projectDto.getProjectId();
        List<MessageEntity> recvMessageList = messageRepository.findAllByProjectIdToMessage_ProjectIdAndUserIdToMessageSend_Uuid(projectId ,sendUserUuid);
        List<MessageDto> messageDtos = new ArrayList<>();

        for (MessageEntity messageEntity : recvMessageList) {
            MessageDto messageDto = new MessageDto();
            messageDto.insertEntity(messageEntity);
            messageDtos.add(messageDto);
        }
        return messageDtos;
    }

    //메세지 하나 확인하기
    public MessageDto selectMessage(Long id) {

        MessageDto messageDto = new MessageDto();
        messageDto.insertEntity(messageRepository.findById(id).get());

        return messageDto;
    }

}
