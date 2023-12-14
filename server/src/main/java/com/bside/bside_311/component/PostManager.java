package com.bside.bside_311.component;

import com.bside.bside_311.entity.Post;
import com.bside.bside_311.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostManager {
  private final PostRepository postRepository;

  public void savePost(Post post) {
    postRepository.save(post);
  }
}
