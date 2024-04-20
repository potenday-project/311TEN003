package com.bside.bside_311.service.component;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.PostTag;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.PostTagRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostTagManager {
	private final PostTagRepository postTagRepository;

	public void deletePostTagByPost(Post post) {
		List<PostTag> postTagList = postTagRepository.findByPost(post);
		postTagList.forEach(postTag -> postTag.setDelYn(YesOrNo.Y));
		postTagRepository.saveAll(postTagList);
	}
}
