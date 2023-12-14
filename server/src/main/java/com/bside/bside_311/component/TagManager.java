package com.bside.bside_311.component;

import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.Tag;
import com.bside.bside_311.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TagManager {
  private final TagService tagService;


  public void registerTagsToPost(Post post, List<String> tagStrList) {
    List<Tag> tags = tagService.addOrSetTags(tagStrList);
    post.removeAllPostTagsAndAddNewPostTags(tags);
  }
}
