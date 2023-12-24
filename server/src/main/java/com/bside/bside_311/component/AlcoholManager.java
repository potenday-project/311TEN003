package com.bside.bside_311.component;

import com.bside.bside_311.entity.Alcohol;
import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.PostAlcohol;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.AlcoholRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlcoholManager {
  private final AlcoholRepository alcoholRepository;

  public void connectAlcoholWithPost(Long alcoholNo, String alcoholFeature, Post post) {
    Alcohol alcohol = alcoholRepository.findByIdAndDelYnIs(alcoholNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException("술이 존재하지 않습니다."));
    PostAlcohol postAlcohol = PostAlcohol.of(post, alcohol, alcoholFeature);
    post.addPostAlcohol(postAlcohol);
    alcohol.addPostAlcohol(postAlcohol);
  }
}
