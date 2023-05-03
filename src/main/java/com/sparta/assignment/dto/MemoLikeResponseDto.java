package com.sparta.assignment.dto;

import com.sparta.assignment.entity.MemoLike;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemoLikeResponseDto {
    private boolean like;

    public MemoLikeResponseDto(MemoLike memoLike) {
        this.like = like;
    }
}
