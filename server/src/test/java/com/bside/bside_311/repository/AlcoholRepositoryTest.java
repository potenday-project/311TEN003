package com.bside.bside_311.repository;

import com.bside.bside_311.dto.AlcoholSearchCondition;
import com.bside.bside_311.entity.Alcohol;
import com.bside.bside_311.entity.AlcoholType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AlcoholRepositoryTest {
  @Autowired
  AlcoholTypeRepository alcoholTypeRepository;

  @Autowired
  AlcoholRepository alcoholRepository;

  @PersistenceContext
  private EntityManager em;

  @Test
  void findByAlcoholType() {
    //given 술타입이 3종류 있다.
    // 그리고 술은 한 8개 있음.
    // 그중에 3개 정도가 내가 조회하려는 타입임.
    //when 술타입으로 조회하면
    //then 3개가 조회됨.
    List<AlcoholType> alcoholTypeList = dataInitAlcoholType();
    List<Alcohol> alcoholList = dataInitAlcohol(alcoholTypeList);

    // when.
    Page<Alcohol> byAlcoholType =
        alcoholRepository.searchAlcoholPage(AlcoholSearchCondition.builder()
                                                                  .alcoholType(
                                                                      alcoholTypeList.get(
                                                                          0).getId())
                                                                  .build(),
            PageRequest.of(0, 10));

    // then.
    List<Alcohol> content = byAlcoholType.getContent();
    content.forEach(System.out::println);
    Assertions.assertThat(content.size()).isEqualTo(4);


  }

  private List<Alcohol> dataInitAlcohol(List<AlcoholType> alcoholTypeList) {
    List<Alcohol> alcoholList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      // 술 10개 만들기
      // alcoholTypeList 기준.
      // 0idx : 4
      // 1idx : 3
      // 2idx : 3
      alcoholList.add(
          Alcohol.builder().name("술" + i).description("설명이다" + i)
                 .alcoholType(alcoholTypeList.get(i % 3)).build());
    }
    alcoholRepository.saveAll(alcoholList);
    em.flush();
    em.clear();
    return alcoholList;
  }

  private List<AlcoholType> dataInitAlcoholType() {
    List<AlcoholType> alcoholTypeList = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      // 알코올 타입 3개 만들기
      alcoholTypeList.add(
          AlcoholType.builder().name("맥주" + i).description("설명이다" + i).displayOrder(i + 1L)
                     .build());
    }
    alcoholTypeRepository.saveAll(alcoholTypeList);
    em.flush();
    em.clear();


    return alcoholTypeList;
  }

}