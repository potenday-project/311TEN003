package com.bside.bside_311.component;

import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.PostAlcohol;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.PostAlcoholRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostAlcoholManager {
  private final PostAlcoholRepository postAlcoholRepository;

  public void deletePostAlcoholByPost(Post post) {
    List<PostAlcohol> postAlcoholList = postAlcoholRepository.findByPost(post);
    postAlcoholList.forEach(postAlcohol -> postAlcohol.setDelYn(YesOrNo.Y));
    postAlcoholRepository.saveAll(postAlcoholList);
  }
}
