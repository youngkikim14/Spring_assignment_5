package com.sparta.assignment.service;

import com.sparta.assignment.entity.*;
import com.sparta.assignment.repository.CommentLikeRepository;
import com.sparta.assignment.repository.CommentRepository;
import com.sparta.assignment.repository.MemoLikeRepository;
import com.sparta.assignment.repository.MemoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final MemoLikeRepository memoLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final MemoRepository memoRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public boolean likeMemo(Long memoid, User user) {
        Memo memo = memoRepository.findById(memoid).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다"));
        Optional<MemoLike> memoLike = memoLikeRepository.findByMemoidAndUserid(memoid, user.getId());
        Boolean like;
        if (memoLike.isPresent()){
            like = memoLike.get().getMlike();
            memoLike.get().setMlike(!like);
        } else {
            memoLikeRepository.save(new MemoLike(user.getId(), memoid, true, memo));
            like = true;
        }
        return like;
    }

    @Transactional
    public boolean likeComment(Long memoid, Long commentid ,User user) {
        Comment comment = commentRepository.findById(commentid).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다"));
        Optional<CommentLike> commentLike = commentLikeRepository.findByMemoidAndCommentidAndUserid(memoid ,commentid, user.getId());
        Boolean like;
        if (commentLike.isPresent()){
            like = commentLike.get().getClike();
            commentLike.get().setClike(!like);
        } else {
            commentLikeRepository.save(new CommentLike(user.getId(), memoid, commentid, true, comment));
            like = true;
        }
        return like;
    }
}