package com.example.bpm.controller;

import com.example.bpm.dto.BlockDto;
import com.example.bpm.dto.DocumentDto;
import com.example.bpm.dto.LogDto;
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

    @Autowired
    private DocumentService documentService;

    //////////////////////////////////////////////////////////////////
    // 페이지 연결
    //////////////////////////////////////////////////////////////////

    // 문서 리스트 Document List
    /// 문서 리스트 관련 페이지 연결
    @GetMapping("/project/document")
    public String getDocumentList(Model model, HttpSession session){

        // needChange

        /*
         유저의 work 목록 확인(user_work 테이블)후 work애 연결된 문서(work_document 테이블)를 받아 처리해줄 필요가 있음
         */
        String userUuid = "1111-1111-test";

        List<DocumentDto> documentDtoList = documentService.getDocumentListByUser(userUuid);

        model.addAttribute("UserDocumentList", documentDtoList);

        // 여기가지 싹 바꿔야함

        return "documentList";
    }

    // 문서 새로 만들기 Document Add [Post]
    /// 새로운 문서를 만드는 작업
    @PostMapping("document/addDocument")
    public String postAddingDocument( /*long workId ,*/ HttpSession session){

        // needChange
        /// work_document 테이블과 연동 필요 (매개 변수로 workID를 받음)

        /// 유저 uuid를 받아서 넣어줘야함
        String userUuid = "1111-1111-test"; // 변경점
        String documentId = documentService.documentAdding(userUuid);

        // 함수 추가 (work_document 테이블 추가)
        //
        //
        //

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

        // needChange
        /// 유저 uuid를 받아서 넣어줘야함
        String userUuid = "1111-1111-test";

        String documentId = documentService.changeLogData(id, userUuid);
        return "redirect:/document/write?id=" + documentId;
    }
}
