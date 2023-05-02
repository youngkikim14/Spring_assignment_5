package com.sparta.assignment.dto;

import com.sparta.assignment.entity.Comment;
import com.sparta.assignment.entity.Memo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemoResponseDto {
    private String title;
    private String username;
    private String contents;
    private List<Comment> comments;

    public MemoResponseDto(Memo memo) {
        this.title = memo.getTitle();
        this.username = memo.getUsername();
        this.contents = memo.getContents();
        this.comments = memo.getComments();
    }
}
