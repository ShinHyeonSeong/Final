package com.example.bpm.controller;

import com.example.bpm.dto.*;
import com.example.bpm.entity.Document;
import com.example.bpm.service.DocumentService;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DocumentController {

    // 서비스 AutoWired
    @Autowired
    private DocumentService documentService;

    //////////////////////////////////////////////////////////////////
    // 페이지 연결
    //////////////////////////////////////////////////////////////////

    // 문서 리스트 Document List
    /// 문서 리스트 관련 페이지 연결
    @GetMapping("document/list")
    public String getDocumentList(Model model, HttpSession session){

        UserDto sessionUser = (UserDto) session.getAttribute("userInfo");

        ProjectDto projectDto = (ProjectDto) session.getAttribute("currentProject");

        String userUuid = sessionUser.getUuid();
        Long projectId = projectDto.getProjectId();

        List<DocumentDto> documentDtoList = documentService.getDocumentListByUser(userUuid);

        List<ProjectDocumentListDto> projectDocumentList = documentService.getDocumentListByProjectId(projectId);

        // 유저가 권한을 가지는 문서들
        model.addAttribute("UserDocumentList", documentDtoList);

        // 현재 프로젝트 문서들
        model.addAttribute("projectDocumentList", projectDocumentList);


        return "documentList";
    }

    // 문서 새로 만들기 Document Add [Post]
    /// 새로운 문서를 만드는 작업
    @PostMapping("document/addDocument")
    public String postAddingDocument( long workId , HttpSession session){

        UserDto sessionUser = (UserDto) session.getAttribute("userInfo");

        String userUuid = sessionUser.getUuid();
        String userName = sessionUser.getName();
        String documentId = documentService.documentAdding(userUuid, userName);

        documentService.workDocumentAdd(workId, documentId);

        return "redirect:/document/write?id=" + documentId;
    }

    // 문서 작성 Document
    /// 문서 작성 페이지 이동
    @GetMapping("document/write")
    public String getDocumentWrite(String id, Model model,HttpSession session){
        DocumentDto documentDto = documentService.getDocumentById(id);
        List<BlockDto> blockDtoList = documentService.getBlockListByDocumentId(id);

        model.addAttribute("document", documentDto);
        model.addAttribute("blockList", blockDtoList);

        UserDto sessionUser = (UserDto) session.getAttribute("userInfo");

        String userUuid = sessionUser.getUuid();

        if(documentService.accreditUserToWork(userUuid, id)){
            return "?";
        }

        return "documentWrite";
    }

    // 로그 페이지
    /// 헤당 문서의 로그 페이지 이동
    @GetMapping("document/log")
    public String getDocumentLog(String id, Model model, HttpSession session){
        List<LogDto> logDtoList = documentService.getLogListById(id);
        model.addAttribute("logList", logDtoList);
        return "documentLog";
    }

    @PostMapping("document/changeLogData")
    public String postDocumentReturnLog(String id, HttpSession session){

        UserDto sessionUser = (UserDto) session.getAttribute("userInfo");

        String userName = sessionUser.getName();

        String documentId = documentService.changeLogData(id, userName);
        return "redirect:/document/write?id=" + documentId;
    }
}
