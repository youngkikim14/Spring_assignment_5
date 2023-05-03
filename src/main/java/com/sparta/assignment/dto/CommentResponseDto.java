package com.sparta.assignment.dto;

import com.sparta.assignment.entity.Comment;
import com.sparta.assignment.entity.CommentLike;
import com.sparta.assignment.entity.MemoLike;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {

    private String contents;
    private String username;
    private Integer commentlikecount;

    public CommentResponseDto(Comment comment) {
        this.contents = comment.getContents();
        this.username = comment.getUsername();
        this.commentlikecount = (int) comment.getCommentLikes().stream().filter(CommentLike::getClike).count();
    }
}
