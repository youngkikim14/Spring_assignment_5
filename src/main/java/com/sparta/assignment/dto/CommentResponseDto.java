package com.sparta.assignment.dto;

import com.sparta.assignment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {

    private String contents;
    private String username;

    public CommentResponseDto(Comment comment) {
        this.contents = comment.getContents();
        this.username = comment.getUsername();
    }
}
