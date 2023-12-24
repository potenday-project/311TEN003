package com.bside.bside_311.component;

import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.PostRepository;
import com.bside.bside_311.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostManager {
  private final PostRepository postRepository;

  public void savePost(Post post) {
    postRepository.save(post);
  }

  public Post findPost(Long postNo) {
    return postRepository.findByIdAndDelYnIs(postNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException(MessageUtil.POST_NOT_FOUND_MSG));
  }

  public void deletePost(Post post) {
    post.setDelYn(YesOrNo.Y);
    postRepository.save(post);
  }
}
