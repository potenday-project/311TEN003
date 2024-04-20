package com.bside.bside_311.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bside.bside_311.entity.Comment;
import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.YesOrNo;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	Optional<Comment> findByIdAndDelYnIs(Long commentNo, YesOrNo delYn);

	List<Comment> findByPostAndDelYnIs(Post post, YesOrNo delYn);
}
