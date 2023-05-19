package com.codingrecipe.board.controller;

import com.codingrecipe.board.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.websocket.server.PathParam;

@Controller
public class HomeController {

    @Autowired
    private final CommentService commentService;

    @GetMapping("/")
    public String index() {
        return "index";
    }


    /* CREATE */
    @PostMapping("/posts/{id}/comments")
    public ResponseEntity commentSave(@PathVariable Long id, @RequestBody CommentRequestDto dto,
                                      @PathParam()) {
        return ResponseEntity.ok(commentService.commentSave(userSessionDto.getNickname(), id, dto));
    }
}
