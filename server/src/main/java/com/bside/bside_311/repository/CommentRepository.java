package com.bside.bside_311.repository;

import com.bside.bside_311.entity.Comment;
import com.bside.bside_311.entity.YesOrNo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  Optional<Comment> findByIdAndDelYnIs(Long commentNo, YesOrNo delYn);
}
