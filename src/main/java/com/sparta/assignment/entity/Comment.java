package com.sparta.assignment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.assignment.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String contents;

    @Column (nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memo_id")
    @JsonBackReference
    private Memo memo;

    public Comment(CommentRequestDto commentRequestDto, Memo memo,User username) {
        this.contents = commentRequestDto.getContents();
        this.username = username.getUsername();
        this.memo = memo;
    }

    public void updateComment (CommentRequestDto commentRequestDto, User username){
        this.contents = commentRequestDto.getContents();
        this.username = username.getUsername();
    }
}
