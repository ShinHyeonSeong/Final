package com.example.bpm.service;

import com.example.bpm.dto.document.BlockDto;
import com.example.bpm.dto.document.DocumentDto;
import com.example.bpm.dto.document.LogDto;
import com.example.bpm.dto.document.json.JsonDocumentDto;
import com.example.bpm.dto.project.relation.ProjectDocumentListDto;
import com.example.bpm.entity.document.BlockEntity;
import com.example.bpm.entity.document.DocumentEntity;
import com.example.bpm.entity.document.LogEntity;
import com.example.bpm.entity.project.data.WorkEntity;
import com.example.bpm.entity.project.relation.WorkDocumentEntity;
import com.example.bpm.entity.user.relation.UserWorkEntity;
import com.example.bpm.repository.*;
import com.example.bpm.service.Logic.dateLogic.DateManager;
import com.example.bpm.service.Logic.logLogic.LogManager;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@Slf4j
public class DocumentService {

    // 필드
    private String bucketName = "bpm-file-storage";

    // 레파지토리 AutoWired
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserWorkRepository userWorkRepository;

    @Autowired
    private WorkDocumentRepository workDocumentRepository;

    @Autowired
    private WorkRepository workRepository;

    // 기타 비지니스 클래스

    private LogManager logManager = new LogManager();

    private DateManager dateManager = new DateManager();

    //////////////////////////////////////////////////////////////////
    // 서비스 로직
    //////////////////////////////////////////////////////////////////

    // 새로운 문서 만들기
    /// 해당 함수를 호출하면 새로운 문서를 만들고 해당 문서의 id 를 반환함
    public String documentAdding(String userUuid, String userName){
        DocumentDto documentDto = new DocumentDto();

        UUID uuid = UUID.randomUUID();

        documentDto.setDocumentId(uuid.toString());
        documentDto.setTitle("제목 없음");
        documentDto.setDateDocument(dateManager.DocumentTime());

        /// 유저 uuid 저장
        documentDto.setUuid(userUuid);

        documentDto.setUserName(userName);

        documentRepository.save(documentDto.toEntity());

        return documentDto.getDocumentId();
    }

    // 문서 제거
    public void deleteDocument(String documentId){
        List<BlockEntity> deleteBlockListEntity = blockRepository.findByDocumentId(documentId);
        List<LogEntity> deleteLogListEntity = logRepository.findByDocumentId(documentId);

        workDocumentRepository.deleteAllByDocumentIdToWorkDocument_DocumentId(documentId);

        for (BlockEntity blockEntity : deleteBlockListEntity) {
            blockRepository.delete(blockEntity);
        }

        for (LogEntity logEntity : deleteLogListEntity) {
            logRepository.delete(logEntity);
        }

        documentRepository.deleteById(documentId);
    }

    // 문서 저장
    public void saveDocument(JsonDocumentDto jsonDocumentDto, String userUuid, String userName){

        DocumentEntity documentEntity = jsonDocumentDto.documentEntityOut();
        documentEntity.setDateDocument(dateManager.DocumentTime());

        documentEntity.setUuid(userUuid);

        documentEntity.setUserName(userName);

        documentRepository.save(documentEntity);

        List<BlockEntity> deleteBlockListEntity = blockRepository.findByDocumentId(jsonDocumentDto.getId());
        List<BlockEntity> addBlockListEntity = jsonDocumentDto.blockEntityOut();

        blockChange(deleteBlockListEntity, addBlockListEntity);

        logReturn(documentEntity, addBlockListEntity, userName + "- Save Document");
    }

    // 로그 데이터로 현재 데이터 교체
    public String changeLogData(String id, String userName){
        LogEntity logEntity = getLogById(id);

        String[] logDocument = logEntity.getLog().split("\\]");
        DocumentEntity documentEntity = logManager.deserializeDocument(logDocument[0]);

        String[] logBlock = logDocument[1].split("\\[");
        List<BlockEntity> deleteBlockListEntity = blockRepository.findByDocumentId(logEntity.getDocumentId());
        List<BlockEntity> addBlockListEntity = new ArrayList<>();

        for (String blockLog : logBlock) {
            BlockEntity blockEntity = logManager.deserializeblock(blockLog);
            addBlockListEntity.add(blockEntity);
        }

        documentRepository.save(documentEntity);

        blockChange(deleteBlockListEntity, addBlockListEntity);

        logReturn(documentEntity, addBlockListEntity, userName + "- Chage Log Data<br>" + logEntity.getDateLog());

        return documentEntity.getDocumentId();
    }

    // 파일 저장
    public String saveFile(MultipartFile file) throws IOException{

        String uuid = UUID.randomUUID().toString();
        String ext = file.getContentType();

        InputStream keyFile = ResourceUtils.getURL("classpath:" + "oceanic-will-385316-249d7b5e0f68.json").openStream();

        Storage storage = StorageOptions.newBuilder().setProjectId("oceanic-will-385316")
                .setCredentials(GoogleCredentials.fromStream(keyFile))
                .build().getService();

        Blob blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, uuid)
                        .setContentType(ext)
                        .build(),
                file.getInputStream()
        );

        return "https://storage.cloud.google.com/bpm-file-storage/"+uuid;
    }


    //work_document 연결
    public void workDocumentAdd(Long workId, String documentId){
        WorkDocumentEntity workDocumentEntity = new WorkDocumentEntity();
        workDocumentEntity.setWorkIdToWorkDocument(workRepository.findByWorkId(workId));
        workDocumentEntity.setDocumentIdToWorkDocument(documentRepository.findByDocumentId(documentId));

        workDocumentRepository.save(workDocumentEntity);
    }

    public boolean accreditUserToWork(String uuid, String DocumentId, Long auth){

        List<UserWorkEntity> userWorkEntityList = userWorkRepository.findAllByUserIdToUserWork_Uuid(uuid);

        WorkDocumentEntity workDocumentEntity = workDocumentRepository.findByDocumentIdToWorkDocument_DocumentId(DocumentId);

        if (auth == 0){
            return false;
        }
        for (UserWorkEntity userWorkEntity: userWorkEntityList) {
            if (userWorkEntity.getWorkIdToUserWork().getWorkId().equals(workDocumentEntity.getWorkIdToWorkDocument().getWorkId()))
                return false;
        }

        return true;

    }

    //////////////////////////////////////////////////////////////////
    // 내부 함수
    //////////////////////////////////////////////////////////////////

    public void blockChange(List<BlockEntity> deleteBlockListEntity, List<BlockEntity> addBlockListEntity) {
        for (BlockEntity blockEntity : deleteBlockListEntity) {
            blockRepository.delete(blockEntity);
        }

        for (BlockEntity blockEntity : addBlockListEntity) {
            blockRepository.save(blockEntity);
        }
    }

    public void logReturn(DocumentEntity documentEntity, List<BlockEntity> blockEntityList, String logType) {
        String logString = "";

        logString += logManager.changeDocumentToString(documentEntity) + "]";

        for (BlockEntity blockEntity : blockEntityList) {
            logString += logManager.changeBlockToString(blockEntity) + "[";
        }

        LogEntity logEntity = new LogEntity();

        UUID uuid = UUID.randomUUID();

        logEntity.setLogId(uuid.toString());
        logEntity.setDocumentId(documentEntity.getDocumentId());
        logEntity.setLog(logString);
        logEntity.setDateLog(dateManager.logTime());
        logEntity.setLogType(logType);

        logRepository.save(logEntity);
    }

    //////////////////////////////////////////////////////////////////
    // 데이터 받아오기
    //////////////////////////////////////////////////////////////////

    /* Document DocumentDto */

    // 문서 전체 받아오기
    public List<DocumentDto> getDocumentList() {
        List<DocumentEntity> documentEntityList = documentRepository.findAll();
        List<DocumentDto> documentDtoList = new ArrayList<>();

        for (DocumentEntity documentEntity :
                documentEntityList)
        {
            DocumentDto documentDto = new DocumentDto();
            documentDto.insertEntity(documentEntity);
            documentDtoList.add(documentDto);
        }

        return documentDtoList;
    }

    // 유저 기준으로 문서 리스트 받아오기
    public List<DocumentDto> getDocumentListByUser(String userUuid){
        List<DocumentEntity> documentEntityList = new ArrayList<>();
        List<DocumentDto> documentDtoList = new ArrayList<>();

        List<UserWorkEntity> userWorkEntityList = userWorkRepository.findAllByUserIdToUserWork_Uuid(userUuid);

        for (UserWorkEntity userWorkEntity : userWorkEntityList) {
            List<WorkDocumentEntity> workDocumentEntityList = workDocumentRepository.findAllByWorkIdToWorkDocument_WorkId(userWorkEntity.getWorkIdToUserWork().getWorkId());
            for (WorkDocumentEntity workDocumentEntity : workDocumentEntityList) {
                documentEntityList.add(documentRepository.findByDocumentId(workDocumentEntity.getDocumentIdToWorkDocument().getDocumentId()));
            }
        }

        for (DocumentEntity documentEntity : documentEntityList)
        {
            DocumentDto documentDto = new DocumentDto();
            documentDto.insertEntity(documentEntity);
            documentDtoList.add(documentDto);
        }

        return documentDtoList;
    }

    // 유저 및 프로젝트 아이디 기준으로 문서 리스트 받아오기
    public  List<DocumentDto> getDocumentListByUserAndProjectId(String userUuid, Long id) {
        List<DocumentDto> documentDtoList = new ArrayList<>();

        List<WorkEntity> workEntityList = workRepository.findAllByProjectIdToWork_ProjectId(id);
        List<WorkEntity> workUserEntityList = new ArrayList<>();

        List<UserWorkEntity> userWorkEntityList = userWorkRepository.findAllByUserIdToUserWork_Uuid(userUuid);

        for (WorkEntity workEntity : workEntityList) {
            for (UserWorkEntity userWorkEntity : userWorkEntityList) {
                if (workEntity.getWorkId() == userWorkEntity.getWorkIdToUserWork().getWorkId()){
                    workUserEntityList.add(workEntity);
                    break;
                }
            }
        }

        for (WorkEntity workEntity: workUserEntityList) {
            documentDtoList.addAll(getDocumentByWorkId(workEntity.getWorkId()));
        }

        return documentDtoList;
    }

    // 문서 아이디 기준으로 문서 받아오기
    public DocumentDto getDocumentById(String id) {
        DocumentEntity documentEntity = documentRepository.findByDocumentId(id);
        DocumentDto documentDto = new DocumentDto();
        documentDto.insertEntity(documentEntity);

        return documentDto;
    }

    // work 기준으로 문서 받아오기
    public List<DocumentDto> getDocumentByWorkId(Long id){
        List<WorkDocumentEntity> workDocumentEntityList = workDocumentRepository.findAllByWorkIdToWorkDocument_WorkId(id);

        List<DocumentEntity> documentEntityList = new ArrayList<>();
        List<DocumentDto> documentDtoList = new ArrayList<>();

        for (WorkDocumentEntity workDocumentEntity: workDocumentEntityList) {
            documentEntityList.add(workDocumentEntity.getDocumentIdToWorkDocument());
        }


        for (DocumentEntity documentEntity : documentEntityList)
        {
            DocumentDto documentDto = new DocumentDto();
            documentDto.insertEntity(documentEntity);
            documentDtoList.add(documentDto);
        }

        return documentDtoList;
    }

    //프로젝트 전체 문서 받아오기
    public List<ProjectDocumentListDto> getDocumentListByProjectId(Long id){
        List<ProjectDocumentListDto> projectDocumentList = new ArrayList<>();
        List<WorkEntity> workEntityList = workRepository.findAllByProjectIdToWork_ProjectId(id);

        for (WorkEntity workEntity: workEntityList) {
            ProjectDocumentListDto projectDocumentListDto = new ProjectDocumentListDto();

            projectDocumentListDto.setWorkName(workEntity.getTitle());

            List<DocumentEntity> documentEntityList = new ArrayList<>();
            List<DocumentDto> documentDtoList = new ArrayList<>();
            List<WorkDocumentEntity> workDocumentEntityList = workDocumentRepository.findAllByWorkIdToWorkDocument_WorkId(workEntity.getWorkId());

            for (WorkDocumentEntity workDocumentEntity: workDocumentEntityList) {
                documentEntityList.add(documentRepository.findByDocumentId(workDocumentEntity.getDocumentIdToWorkDocument().getDocumentId()));
            }

            for (DocumentEntity documentEntity : documentEntityList)
            {
                DocumentDto documentDto = new DocumentDto();
                documentDto.insertEntity(documentEntity);
                documentDtoList.add(documentDto);
            }

            projectDocumentListDto.setDocumentDtoList(documentDtoList);

            projectDocumentList.add(projectDocumentListDto);
        }

        return projectDocumentList;
    }

    /* Block BlockDto */

    // 문서 아이디 기준으로 블럭 리스트 받아오기
    public List<BlockDto> getBlockListByDocumentId(String id) {
        List<BlockEntity> blockEntityList = blockRepository.findByDocumentId(id);
        List<BlockDto> blockDtoList = new ArrayList<>();

        for (BlockEntity blockEntity :
                blockEntityList) {
            BlockDto blockDto = new BlockDto();
            blockDto.insertEntity(blockEntity);
            blockDtoList.add(blockDto);
        }

        Collections.sort(blockDtoList);

        return blockDtoList;
    }

    /* Log LogDto */

    // 문서 아이디 기준으로 문서 리스트 불러오기
    public List<LogDto> getLogListById(String id) {
        List<LogEntity> logEntityList = logRepository.findByDocumentId(id);
        List<LogDto> logDtoList = new ArrayList<>();

        for (LogEntity logEntity : logEntityList) {
            LogDto logDto = new LogDto();
            logDto.insertEntity(logEntity);
            logDtoList.add(logDto);
        }

        Collections.sort(logDtoList, Collections.reverseOrder());

        return logDtoList;
    }

    // 로그 아이디로 로그 불러오기 [서비스 내부에서 사용]
    public LogEntity getLogById(String id) {
        return logRepository.findBylogId(id);
    }

}