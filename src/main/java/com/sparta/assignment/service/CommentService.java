package com.sparta.assignment.service;

import com.sparta.assignment.dto.CommentRequestDto;
import com.sparta.assignment.dto.CommentResponseDto;
import com.sparta.assignment.entity.Comment;
import com.sparta.assignment.entity.Memo;
import com.sparta.assignment.entity.User;
import com.sparta.assignment.entity.UserRoleEnum;
import com.sparta.assignment.jwt.JwtUtil;
import com.sparta.assignment.repository.CommentRepository;
import com.sparta.assignment.repository.MemoRepository;
import com.sparta.assignment.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public CommentResponseDto createComment(Long memoid, CommentRequestDto commentRequestDto, User user) {
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
        Memo memo = memoRepository.findById(memoid).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다")
        );
        Comment comment = new Comment(commentRequestDto, memo, user);
        commentRepository.saveAndFlush(comment);

        return new CommentResponseDto(comment);
    }


    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, Long memoid, User user) {
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
        memoRepository.findById(memoid).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다")
        );
        Comment comment = commentRepository.findById(id).orElseThrow( // 없는 글 null 처리
                () -> new NullPointerException("존재하지 않은 댓글입니다.")
        );
        UserRoleEnum userRoleEnum = user.getUserRoleEnum();
        if (comment.getUsername().equals(user.getUsername()) || userRoleEnum == UserRoleEnum.ADMIN) {
            comment.updateComment(commentRequestDto, user);
            return new CommentResponseDto(comment);
        } else {
            throw new IllegalArgumentException("권한이 없습니다");
        }
    }


    @Transactional
    public String deleteComment(Long id, Long memoid, User user) {
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
        memoRepository.findById(memoid).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다")
        );
        Comment comment = commentRepository.findById(id).orElseThrow( // 없는 글 null 처리
                () -> new NullPointerException("존재하지 않은 게시글입니다.")
        );
        UserRoleEnum userRoleEnum = user.getUserRoleEnum();
        if (comment.getUsername().equals(user.getUsername()) || userRoleEnum == UserRoleEnum.ADMIN) {
            commentRepository.deleteById(id);
            return "댓글이 삭제 되었습니다";
        } else {
            throw new IllegalArgumentException("권한이 없습니다");
        }
    }
}