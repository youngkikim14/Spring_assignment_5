package com.sparta.assignment.repository;

import com.sparta.assignment.entity.MemoLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoLikeRepository extends JpaRepository<MemoLike, Long> {
}
