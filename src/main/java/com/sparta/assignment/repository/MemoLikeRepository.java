package com.sparta.assignment.repository;

import com.sparta.assignment.entity.MemoLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemoLikeRepository extends JpaRepository<MemoLike, Long> {
    Optional<MemoLike> findByMemoidAndUserid(Long memoid, Long userid);
}
