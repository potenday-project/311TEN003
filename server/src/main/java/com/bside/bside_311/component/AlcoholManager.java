package com.bside.bside_311.component;

import com.bside.bside_311.dto.AlcoholSearchCondition;
import com.bside.bside_311.entity.Alcohol;
import com.bside.bside_311.entity.AlcoholType;
import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.PostAlcohol;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.AlcoholRepository;
import com.bside.bside_311.repository.AlcoholTypeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlcoholManager {
  private final AlcoholRepository alcoholRepository;
  private final AlcoholTypeRepository alcoholTypeRepository;

  public void connectAlcoholWithPost(Long alcoholNo, String alcoholFeature, Post post) {
    Alcohol alcohol = alcoholRepository.findByIdAndDelYnIs(alcoholNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException("술이 존재하지 않습니다."));
    PostAlcohol postAlcohol = PostAlcohol.of(post, alcohol, alcoholFeature);
    post.addPostAlcohol(postAlcohol);
    alcohol.addPostAlcohol(postAlcohol);
  }

  public Page<Alcohol> searchAlcohol(Pageable pageable, String searchKeyword,
                                     Long alcoholType) {
    if (ObjectUtils.isNotEmpty(alcoholType)) {
      AlcoholType alcoholType1 = alcoholTypeRepository.findByIdAndDelYnIs(alcoholType, YesOrNo.N)
                                                      .orElseThrow(
                                                          () -> new IllegalArgumentException(
                                                              "술 종류가 존재하지 않습니다."));
    }
    // 술 종류 fetch join
    return alcoholRepository.searchAlcoholPage(AlcoholSearchCondition.builder()
                                                                     .searchKeyword(
                                                                         searchKeyword)
                                                                     .alcoholType(
                                                                         alcoholType)
                                                                     .build(),
        pageable);
  }
}
