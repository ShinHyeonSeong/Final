package com.example.bpm.controller;

import com.example.bpm.dto.*;
import com.example.bpm.entity.Document;
import com.example.bpm.entity.UserEntity;
import com.example.bpm.service.DocumentService;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DocumentController {

    @Autowired
    private DocumentService documentService;
    @Autowired
    private HttpSession session;

    // 생각을 해보니 말야 매번 세션 호출하는것보다는 그냥 따로 메서드 만드는게 훨씬 효율이 좋을듯. 편하기도 하고

    public UserDto getSessionUser() {
        UserDto currentUser = (UserDto) session.getAttribute("userInfo");
        return currentUser;
    }


    //////////////////////////////////////////////////////////////////
    // 페이지 연결
    //////////////////////////////////////////////////////////////////

    // 문서 리스트 Document List
    /// 문서 리스트 관련 페이지 연결
    @GetMapping("/project/documentList")
    public String getDocumentList(Model model, HttpSession session) {

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
    public String postAddingDocument( /*long workId ,*/ HttpSession session) {

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
    public String getDocumentWrite(String id, Model model, HttpSession session) {
        DocumentDto documentDto = documentService.getDocumentById(id);
        List<BlockDto> blockDtoList = documentService.getBlockListByDocumentId(id);

        model.addAttribute("document", documentDto);
        model.addAttribute("blockList", blockDtoList);
        return "documentWrite";
    }

    // 로그 페이지
    /// 헤당 문서의 로그 페이지 이동
    @GetMapping("document/log")
    public String getDocumentLog(String id, Model model, HttpSession session) {
        List<LogDto> logDtoList = documentService.getLogListById(id);
        model.addAttribute("logList", logDtoList);
        return "documentLog";
    }

    @PostMapping("document/changeLogData")
    public String postDocumentReturnLog(String id, HttpSession session) {

        // needChange
        /// 유저 uuid를 받아서 넣어줘야함
        String userUuid = "1111-1111-test";

        String documentId = documentService.changeLogData(id, userUuid);
        return "redirect:/document/write?id=" + documentId;
    }


    /* - - - - - -  CommmentController - - - - - - */
    //documentID는 그냥 코드로만 적용하고 실제 보여지는창에는 안보이게 해야댐
    @PostMapping("댓글작성 클릭 시")
    public String plusComment(@RequestParam("comment") String comment, HttpSession session, Model model) {
        /* 댓글을 추가 시키는 메서드*/
        Document nowdocument = (Document) session.getAttribute("현재 document 세션 값");
        UserDto nowUser = getSessionUser();
        DocumentCommentDto documentCommentDto = new DocumentCommentDto();
        documentCommentDto.setComment(comment);
        documentCommentDto.setDocumentIdToComment(nowdocument);
        documentCommentDto.setUserIdToComment(UserEntity.toUserEntity(nowUser));
        /* 댓글을 추가 시키는 메서드 끝 */

        /*추가 시킬 댓글 내용과, 현재 documentID 를 같이 넘겨 리턴 값으로 자동 리스트를 뽑아온다*/
        List<DocumentCommentDto> list = documentService.plusComment(documentCommentDto, nowdocument.getDocumentId());
        model.addAttribute("commentList", list);
        return "";
    }

    @GetMapping("댓글 수정 클릭 시")
    public String updateForm(@RequestParam("commentId") Long commentId, Model model) {
        DocumentCommentDto updateComment = documentService.findComment(commentId);
        model.addAttribute("updateComment", updateComment);

        return "";
    }

    @PostMapping("댓글 수정 완료 시")
    public String updateComment(@RequestParam("comment") String comment, HttpSession session, Model model) {
        Document nowdocument = (Document) session.getAttribute("현재 document 세션 값");
        UserDto nowUser = getSessionUser();
        DocumentCommentDto documentCommentDto = new DocumentCommentDto();
        documentCommentDto.setComment(comment);
        documentCommentDto.setDocumentIdToComment(nowdocument);
        documentCommentDto.setUserIdToComment(UserEntity.toUserEntity(nowUser));

        List<DocumentCommentDto> list = documentService.updateComment(documentCommentDto, nowdocument.getDocumentId());
        model.addAttribute("commentList", list);
        return "";
    }

    @GetMapping(" 삭제 클릭 시 ")
    public String deleteComment(@RequestParam("documentId") Long documentId, Model model) {
        List<DocumentCommentDto> dtoList = documentService.deleteComment(documentId);
        model.addAttribute("commentList", dtoList);
        return "";
    }
}
