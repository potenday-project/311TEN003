package com.bside.bside_311.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.PostLike;
import com.bside.bside_311.entity.User;
import com.bside.bside_311.entity.YesOrNo;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
	Optional<PostLike> findByUserAndPostAndDelYnIs(User user, Post post, YesOrNo delYn);

	Long countByPostAndDelYnIs(Post post, YesOrNo delYn);
}
