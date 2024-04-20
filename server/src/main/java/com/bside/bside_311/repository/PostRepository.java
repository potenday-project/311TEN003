package com.bside.bside_311.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.YesOrNo;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
	Optional<Post> findByIdAndDelYnIs(Long postNo, YesOrNo delYn);
}
