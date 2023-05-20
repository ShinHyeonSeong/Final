package com.codingrecipe.board.dto;

import com.codingrecipe.board.entity.CommentEntity;
import com.codingrecipe.board.entity.PostEntity;
import com.codingrecipe.board.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentDto {
    private Long commentId;
    private String content;
    private Date date;
    private UserEntity uuidToReply;
    private PostEntity postIdToReply;

    public static CommentDto toCommentDto(CommentEntity commentEntity) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(commentEntity.getCommentId());
        commentDto.setContent(commentEntity.getContent());
        commentDto.setDate(commentEntity.getDate());
        commentDto.setUuidToReply(commentEntity.getUuidToReply());
        commentDto.setPostIdToReply(commentEntity.getPostIdToReply());

        return commentDto;
    }

}
