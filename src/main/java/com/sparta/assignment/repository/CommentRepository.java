package com.sparta.assignment.repository;

import com.sparta.assignment.entity.Comment;
import com.sparta.assignment.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

//    Optional<Comment> findByIdAndUsername(Long id, String username); 필요 없을듯

}
