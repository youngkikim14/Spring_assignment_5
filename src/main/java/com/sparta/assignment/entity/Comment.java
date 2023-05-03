package com.sparta.assignment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.assignment.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column (nullable = false)
    private String contents;

    @Column (nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memo_id")
    private Memo memo;

    @OneToMany
    @JoinColumn(name = "commentlikes_id")
    private List<CommentLike> commentLikes;

    public Comment(CommentRequestDto commentRequestDto, Memo memo,User username) {
        this.contents = commentRequestDto.getContents();
        this.username = username.getUsername();
        this.memo = memo;
//        memo.getComments().add(this);
    }

    public void updateComment (CommentRequestDto commentRequestDto, User username){
        this.contents = commentRequestDto.getContents();
        this.username = username.getUsername();
    }
}
