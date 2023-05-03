package com.sparta.assignment.service;

import com.sparta.assignment.dto.MemoRequestDto;
import com.sparta.assignment.dto.MemoResponseDto;
import com.sparta.assignment.entity.Memo;
import com.sparta.assignment.entity.User;
import com.sparta.assignment.entity.UserRoleEnum;
import com.sparta.assignment.jwt.JwtUtil;
import com.sparta.assignment.repository.CommentLikeRepository;
import com.sparta.assignment.repository.MemoLikeRepository;
import com.sparta.assignment.repository.MemoRepository;
import com.sparta.assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final MemoLikeRepository memoLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public MemoResponseDto createMemo(MemoRequestDto requestDto, User user) {
            Memo memo = new Memo(requestDto, user.getUsername());
            memoRepository.saveAndFlush(memo);

            return new MemoResponseDto(memo);
    }

    @Transactional(readOnly = true) //전체 글 조회
    public List<MemoResponseDto> getMemos() {
        List<Memo> memos = memoRepository.findAllByOrderByModifiedAtDesc();
        List<MemoResponseDto> memoResponseDtos = new ArrayList<>();
        for (Memo memo: memos) {
            memoResponseDtos.add(new MemoResponseDto(memo));
        }
        return memoResponseDtos;
    }

    @Transactional(readOnly = true)
    public MemoResponseDto getMemo(Long id) {
        Memo memo = memoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다"));
        return new MemoResponseDto(memo);
    }

    @Transactional
    public MemoResponseDto update(Long id, MemoRequestDto requestDto, User user) {
            Memo memo = memoRepository.findById(id).orElseThrow( // 없는 글 null 처리
                    () -> new NullPointerException("존재하지 않은 게시글입니다.")
            );
            UserRoleEnum userRoleEnum = user.getUserRoleEnum();
            if (user.getUsername().equals(memo.getUsername()) || userRoleEnum == UserRoleEnum.ADMIN){
                memo.update(requestDto, user.getUsername());
                return new MemoResponseDto(memo);
            } else {
                throw new IllegalArgumentException("권한이 없습니다");
                    }
        }


    @Transactional
    public String deleteMemo(Long id, User user) {
            Memo memo = memoRepository.findById(id).orElseThrow( // 없는 글 null 처리
                    () -> new NullPointerException("존재하지 않은 게시글입니다.")
            );
            UserRoleEnum userRoleEnum = user.getUserRoleEnum();
            if (user.getUsername().equals(memo.getUsername()) || userRoleEnum == UserRoleEnum.ADMIN) {
                memoRepository.deleteById(id);
                return "삭제 완료";
            } else {
                throw new IllegalArgumentException("권한이 없습니다");
            }
        }
    }

