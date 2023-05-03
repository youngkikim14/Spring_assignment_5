package com.sparta.assignment.dto;

import com.sparta.assignment.entity.Comment;
import com.sparta.assignment.entity.Memo;
import com.sparta.assignment.entity.MemoLike;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemoResponseDto {
    private String title;
    private String username;
    private String contents;
    private List<CommentResponseDto> comments;
    private Integer memolikecount;

    public MemoResponseDto(Memo memo) {
        this.title = memo.getTitle();
        this.username = memo.getUsername();
        this.contents = memo.getContents();
        this.comments = new ArrayList<>();
        for (Comment comment : memo.getComments()) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            this.comments.add(commentResponseDto);
        }
        //this.memolikecount = (int) memo.getMemoLikes().stream().filter(n -> n.getMlike() == true).count();
        //this.memolikecount = (int) memo.getMemoLikes().stream().filter(MemoLike::getMlike).count();


            int count = 0;
            List<MemoLike> memoLikes  = memo.getMemoLikes();
            for (MemoLike n : memoLikes){
            boolean trueOrNot = n.getMlike();

            if (trueOrNot){
                count++;
            }
        }
        this.memolikecount = count;




    }
}
