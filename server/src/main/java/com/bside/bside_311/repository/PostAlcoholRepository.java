package com.bside.bside_311.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.PostAlcohol;

public interface PostAlcoholRepository extends JpaRepository<PostAlcohol, Long> {
	List<PostAlcohol> findByPost(Post post);
}
