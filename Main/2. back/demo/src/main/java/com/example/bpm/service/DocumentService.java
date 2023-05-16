package com.example.bpm.service;

import com.example.bpm.dto.BlockDto;
import com.example.bpm.dto.DocumentDto;
import com.example.bpm.dto.JsonDocumentDto;
import com.example.bpm.dto.LogDto;
import com.example.bpm.entity.Block;
import com.example.bpm.entity.Document;
import com.example.bpm.entity.Log;
import com.example.bpm.repository.BlockRepository;
import com.example.bpm.repository.DocumentRepository;
import com.example.bpm.repository.LogRepository;
import com.example.bpm.service.dateLogic.DateManager;
import com.example.bpm.service.logLogic.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentService {

    // 레파지토리 AutoWired

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private LogRepository logRepository;

    // 기타 비지니스 클래스

    private LogManager logManager = new LogManager();

    private DateManager dateManager = new DateManager();

    //////////////////////////////////////////////////////////////////
    // 서비스 로직
    //////////////////////////////////////////////////////////////////

    // 새로운 문서 만들기
    /// 해당 함수를 호출하면 새로운 문서를 만들고 해당 문서의 id 를 반환함
    public String documentAdding(String userUuid){
        DocumentDto documentDto = new DocumentDto();

        UUID uuid = UUID.randomUUID();

        documentDto.setDocumentId(uuid.toString());
        documentDto.setTitle("제목 없음");
        documentDto.setDateDocument(dateManager.DocumentTime());

        /// 유저 uuid 저장
        documentDto.setUuid(userUuid);

        // needChange
        /// 유저 이름 찾아서 저장
        String userName = "임시 유저"; // 변경할 부분
        documentDto.setUserName(userName);

        documentRepository.save(documentDto.toEntity());

        return documentDto.getDocumentId();
    }

    // 문서 저장
    public void saveDocument(JsonDocumentDto jsonDocumentDto, String userUuid){

        Document document = jsonDocumentDto.documentEntityOut();
        document.setDateDocument(dateManager.DocumentTime());

        /// 유저 uuid 저장
        document.setUuid(userUuid);

        // needChange
        /// 유저 이름 찾아서 저장
        String userName = "임시 유저"; // 변경할 부분
        document.setUserName(userName);

        documentRepository.save(document);

        List<Block> deleteBlockList = blockRepository.findByDocumentId(jsonDocumentDto.getId());
        List<Block> addBlockList = jsonDocumentDto.blockEntityOut();

        blockChange(deleteBlockList, addBlockList);

        logReturn(document, addBlockList,  userName + "- Save Document");
    }

    // 로그 데이터로 현재 데이터 교체
    public String changeLogData(String id, String userUuid){
        Log log = getLogById(id);

        String[] logDocument = log.getLog().split("\\]");
        Document document = logManager.deserializeDocument(logDocument[0]);

        String[] logBlock = logDocument[1].split("\\/");
        List<Block> deleteBlockList = blockRepository.findByDocumentId(log.getDocumentId());
        List<Block> addBlockList = new ArrayList<>();

        for (String blockLog : logBlock) {
            Block block = logManager.deserializeblock(blockLog);
            addBlockList.add(block);
        }

        documentRepository.save(document);

        blockChange(deleteBlockList, addBlockList);

        // needChange
        /// 유저 이름 찾아서 저장 ()
        String userName = "임시 유저"; // 변경할 부분

        logReturn(document, addBlockList, userName + "- Chage Log Data<br>" + log.getDateLog());

        return document.getDocumentId();
    }

    // 파일 저장
    public String saveFile(MultipartFile file){

        if (file != null) {
            // 파일 저장 경로
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\file\\image";

            // 랜덤 UUID를 생성 하여 UUID_본래 파일 이름 으로 파일 이름 생성
            UUID uuid = UUID.randomUUID();

            String fileName = uuid + "_" + file.getOriginalFilename();

            // 파일이 비어있지 않을때, 파일에 파일 명, 파일 경로 저장
            if (file.getOriginalFilename() != "") {
                File saveFile = new File(projectPath, fileName);

                // 전달할 파일 저장
                try {
                    file.transferTo(saveFile);
                }
                catch (Exception e){
                    return "error";
                }
            }

            return "/file/image/" + fileName;
        }

        return "error";
    }

    //////////////////////////////////////////////////////////////////
    // 내부 함수
    //////////////////////////////////////////////////////////////////

    public void blockChange(List<Block> deleteBlockList, List<Block> addBlockList){
        for (Block block : deleteBlockList) {
            blockRepository.delete(block);
        }

        for (Block block : addBlockList) {
            blockRepository.save(block);
        }
    }

    public void logReturn(Document document, List<Block> blockList, String logType){
        String logString = "";

        logString += logManager.changeDocumentToString(document) + "]";

        for (Block block: blockList) {
            logString += logManager.changeBlockToString(block) + "/";
        }

        Log log = new Log();

        UUID uuid = UUID.randomUUID();

        log.setLogId(uuid.toString());
        log.setDocumentId(document.getDocumentId());
        log.setLog(logString);
        log.setDateLog(dateManager.logTime());
        log.setLogType(logType);

        logRepository.save(log);
    }

    //////////////////////////////////////////////////////////////////
    // 데이터 받아오기
    //////////////////////////////////////////////////////////////////

    /* Document DocumentDto */

    // 문서 전체 받아오기
    public List<DocumentDto> getDocumentList(){
        List<Document> documentList = documentRepository.findAll();
        List<DocumentDto> documentDtoList = new ArrayList<>();

        for (Document document:
        documentList)
        {
            DocumentDto documentDto = new DocumentDto();
            documentDto.insertEntity(document);
            documentDtoList.add(documentDto);
        }

        return documentDtoList;
    }

    // 유저 기준으로 문서 리스트 받아오기
    public List<DocumentDto> getDocumentListByUser(String userUuid){
        List<Document> documentList = documentRepository.findByUuid(userUuid);
        List<DocumentDto> documentDtoList = new ArrayList<>();

        for (Document document:
                documentList)
        {
            DocumentDto documentDto = new DocumentDto();
            documentDto.insertEntity(document);
            documentDtoList.add(documentDto);
        }

        return documentDtoList;
    }

    // 문서 아이디 기준으로 문서 받아오기
    public DocumentDto getDocumentById(String id){
        Document document =  documentRepository.findByDocumentId(id);
        DocumentDto documentDto = new DocumentDto();
        documentDto.insertEntity(document);

        return documentDto;
    }

    /* Block BlockDto */

    // 문서 아이디 기준으로 블럭 리스트 받아오기
    public  List<BlockDto> getBlockListByDocumentId(String id){
        List<Block> blockList = blockRepository.findByDocumentId(id);
        List<BlockDto> blockDtoList = new ArrayList<>();

        for (Block block:
        blockList) {
            BlockDto blockDto = new BlockDto();
            blockDto.insertEntity(block);
            blockDtoList.add(blockDto);
        }

        Collections.sort(blockDtoList);

        return  blockDtoList;
    }

    /* Log LogDto */

    // 문서 아이디 기준으로 문서 리스트 불러오기
    public  List<LogDto> getLogListById(String id){
        List<Log> logList = logRepository.findByDocumentId(id);
        List<LogDto> logDtoList = new ArrayList<>();

        for (Log log : logList) {
            LogDto logDto = new LogDto();
            logDto.insertEntity(log);
            logDtoList.add(logDto);
        }

        Collections.sort(logDtoList, Collections.reverseOrder());

        return logDtoList;
    }

    // 로그 아이디로 로그 불러오기 [서비스 내부에서 사용]
    public  Log getLogById(String id){
        return  logRepository.findBylogId(id);
    }
}