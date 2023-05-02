package com.sparta.assignment.service;

import com.sparta.assignment.dto.CommentResponseDto;
import com.sparta.assignment.dto.MemoRequestDto;
import com.sparta.assignment.dto.MemoResponseDto;
import com.sparta.assignment.entity.Comment;
import com.sparta.assignment.entity.Memo;
import com.sparta.assignment.entity.User;
import com.sparta.assignment.entity.UserRoleEnum;
import com.sparta.assignment.jwt.JwtUtil;
import com.sparta.assignment.repository.MemoRepository;
import com.sparta.assignment.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
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

    @Transactional
    public MemoResponseDto createMemo(MemoRequestDto requestDto, User user) { //토큰값 가져와서 검증
//        String token = jwtUtil.resolveToken(request); //토큰값
//        Claims claims;
//
//        if (token != null) {
//            if (jwtUtil.validateToken(token)) {
//                claims = jwtUtil.getUserInfoFromToken(token); //토큰으로 사용자 정보 가져오기
//            } else {
//                throw new IllegalArgumentException("Token Erro"); // 에러표시
//            }
//            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow( //토큰이 맞으면 토큰으로 db에서 사용자 정보 조회
//                    () -> new IllegalArgumentException("없는 유저입니다")
//            );
            Memo memo = new Memo(requestDto, user.getUsername());
            memoRepository.saveAndFlush(memo);

            return new MemoResponseDto(memo);
//        } else {
//            return null;
//        }
    }

    @Transactional(readOnly = true) //전체 글 조회
    public List<MemoResponseDto> getMemos() {
        List<Memo> memos = memoRepository.findAllByOrderByModifiedAtDesc();
        List<MemoResponseDto> memoResponseDtos = new ArrayList<>();
        for (Memo memo: memos) {
//            for (Comment comment: memo.getComments()) {
//                String commentcontent = comment.getContents();
//            }
            memoResponseDtos.add(new MemoResponseDto(memo));
        }
        return memoResponseDtos;
    }


    @Transactional(readOnly = true) // responsedto 작성으로 보여주고 싶은 내용만 만들어냄
    public Memo getMemo(Long id) {
        Memo memo = memoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다"));
        List<Comment> comments = memo.getComments();
        for (Comment comment: comments) {
            String commentcontent = comment.getContents();
        }
        return memo;
    }


    @Transactional
    public MemoResponseDto update(Long id, MemoRequestDto requestDto, User user) {
//        String token = jwtUtil.resolveToken(request); //토큰값
//        Claims claims;
//
//        if (token != null) {
//            if (jwtUtil.validateToken(token)) {
//                claims = jwtUtil.getUserInfoFromToken(token); //토큰으로 사용자 정보 가져오기
//            } else {
//                throw new IllegalArgumentException("Token Erro"); // 에러표시
//            }
//            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow( //토큰이 맞으면 토큰으로 db에서 사용자 정보 조회
//                    () -> new IllegalArgumentException("없는 유저입니다")
//            );
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
//        String token = jwtUtil.resolveToken(request); //토큰값
//        Claims claims;
//
//        if (token != null) {
//            if (jwtUtil.validateToken(token)) {
//                claims = jwtUtil.getUserInfoFromToken(token); //토큰으로 사용자 정보 가져오기
//            } else {
//                throw new IllegalArgumentException("Token Erro"); // 에러표시
//            }
//            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow( //토큰이 맞으면 토큰으로 db에서 사용자 정보 조회
//                    () -> new IllegalArgumentException("없는 유저입니다")
//            );
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

