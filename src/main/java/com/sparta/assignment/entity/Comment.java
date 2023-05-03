package com.sparta.assignment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.assignment.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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

    @ManyToOne
    @JoinColumn(name = "memo_id")
    private Memo memo;

    @OneToMany(mappedBy = "comment" ,fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CommentLike> commentLikes = new ArrayList<>();

    public Comment(CommentRequestDto commentRequestDto, Memo memo, User username) {
        this.contents = commentRequestDto.getContents();
        this.username = username.getUsername();
        this.memo = memo;
        memo.getComments().add(this);
    }

    public void updateComment (CommentRequestDto commentRequestDto, User username){
        this.contents = commentRequestDto.getContents();
        this.username = username.getUsername();
    }
}
