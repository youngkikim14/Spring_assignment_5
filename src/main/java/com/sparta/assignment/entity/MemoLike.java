package com.sparta.assignment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MemoLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long userid;

    @Column
    private Long memoid;

    @Column
    private Boolean mlike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMO_ID")
    private Memo memo;


    public MemoLike(Long userid, Long memoid, Boolean like, Memo memo) {
        this.userid = userid;
        this.memoid = memoid;
        this.mlike = like;
        this.memo = memo;
    }
}
