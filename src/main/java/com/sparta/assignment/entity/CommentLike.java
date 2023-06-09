package com.sparta.assignment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long userid;

    @Column
    private Long memoid;

    @Column
    private Long commentid;

    @Column
    private Boolean clike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;


    public CommentLike(Long userid, Long memoid, Long commentid, Boolean like, Comment comment) {
        this.userid = userid;
        this.memoid = memoid;
        this.commentid = commentid;
        this.clike = like;
        this.comment = comment;
    }
}