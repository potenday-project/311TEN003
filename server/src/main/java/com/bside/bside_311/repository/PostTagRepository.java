package com.bside.bside_311.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.PostTag;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {

	List<PostTag> findByPost(Post post);
}
