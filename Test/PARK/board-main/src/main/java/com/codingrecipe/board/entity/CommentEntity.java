package com.codingrecipe.board.entity;

import javax.persistence.*;
import java.util.Date;

import com.codingrecipe.board.dto.CommentDto;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column
    private String content;

    @Column
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid")
    private UserEntity uuidToReply;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private PostEntity postIdToReply;

    public static CommentEntity toCommentEntity(CommentDto commentDto) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentId(commentDto.getCommentId());
        commentEntity.setContent(commentDto.getContent());
        commentEntity.setDate(commentDto.getDate());
        commentEntity.setUuidToReply(commentDto.getUuidToReply());
        commentEntity.setPostIdToReply(commentEntity.getPostIdToReply());
        return commentEntity;
    }
}
