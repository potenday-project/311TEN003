package com.bside.bside_311.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bside.bside_311.dto.PostSearchCondition;
import com.bside.bside_311.entity.Post;

public interface PostRepositoryCustom {
	Page<Post> searchPageSimple(PostSearchCondition condition, Pageable pageable);

	Page<Post> searchPagePopular(Long page, Long size);
}
