package com.example.document.controller;

import com.example.document.dto.JsonDocumentDto;
import com.example.document.service.DocumentService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class DocumentRestController {
    //필드
    String fileName = "" ;

    // 서비스 AutoWired
    @Autowired
    private DocumentService documentService;

    // 문서 저장 json
    @PostMapping("/document/save")
    @ResponseBody
    public boolean postDocumentSave(@RequestBody Map<String, Object> data, HttpSession session) {

        JsonDocumentDto jsonDocumentDto = new JsonDocumentDto();

        jsonDocumentDto.setTitle(data.get("title").toString());
        jsonDocumentDto.setId(data.get("id").toString());

        jsonDocumentDto.setBlockList((ArrayList<LinkedHashMap>)data.get("blockList"));

        // needChange
        /// 유저 uuid를 받아서 넣어줘야함
        String userUuid = "1111-1111-test";
        documentService.saveDocument(jsonDocumentDto, userUuid);

        return true;

    }

    //파일 저장
    @PostMapping("/fileUpload")
    public void fileUpload(MultipartFile[] uploadFile){

        fileName = documentService.saveFile(uploadFile[0]);
    }

    //파일 저장 정보 프론트로 반환
    @RequestMapping(value = "/fileName/return", method = { RequestMethod.GET })
    public void m1(HttpServletResponse resp) {

        try {
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");

            String file = fileName;

            PrintWriter writer = resp.getWriter();
            writer.print(file);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            fileName = "";
        }
    }
}
