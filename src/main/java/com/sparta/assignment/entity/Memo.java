package com.sparta.assignment.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.assignment.dto.MemoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Setter
@NoArgsConstructor
public class Memo extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

    @OneToMany (mappedBy = "memo", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("modifiedAt DESC")
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();


    public Memo(MemoRequestDto requestDto, String username) {

        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.username = username;
    }

    public void update(MemoRequestDto requestDto, String username) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.username = username;
    }
}