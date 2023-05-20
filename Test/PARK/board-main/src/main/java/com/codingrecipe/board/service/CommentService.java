package com.codingrecipe.board.service;

import com.codingrecipe.board.dto.CommentDto;
import com.codingrecipe.board.entity.CommentEntity;
import com.codingrecipe.board.entity.UserEntity;
import com.codingrecipe.board.repository.DocumentRepository;
import com.codingrecipe.board.repository.CommentRepository;
import com.codingrecipe.board.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CommentService {

    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final UserRepository userRepository;

//    @Transactional
//    public Long commentSave(CommentDto commentDto) {
//        UserEntity user = userRepository.findByNickname(nickname);
//        Posts posts = postsRepository.findById(id).orElseThrow(() ->
//                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다." + id));
//
//        dto.setUser(user);
//        dto.setPosts(posts);
//
//        Comment comment = dto.toEntity();
//        commentRepository.save(comment);
//
//        return dto.getId();
//    }

    public CommentDto commentPlus(CommentDto commentDto) {
        if (commentDto.equals(null)) {
            log.info("올바른 댓글을 입력 받지 못함 (서비스 작동)");
            return null;
        } else {
            log.info("올바른 댓글을 입력 받아 DB 저장 (서비스 작동)");
            commentRepository.save(CommentEntity.toCommentEntity(commentDto));
            return commentDto;
        }
    }

    public List<CommentDto> findAllByComment(Long documentId) {
        List<CommentEntity> commentEntityList = commentRepository.findCommentEntitiesByUUID(documentId);
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (CommentEntity commentEntity : commentEntityList) {
            commentDtoList.add(CommentDto.toCommentDto(commentEntity));
        }
        log.info("리스트 불러오기 성공 (서비스 작동)");
        return commentDtoList;
    }
}
