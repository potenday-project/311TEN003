package com.bside.bside_311.service.component;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.Tag;
import com.bside.bside_311.service.TagService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TagManager {
	private final TagService tagService;

	public void registerTagsToPost(Post post, List<String> tagStrList) {
		List<Tag> tags = tagService.addOrSetTags(tagStrList);
		post.removeAllPostTagsAndAddNewPostTags(tags);
	}
}
