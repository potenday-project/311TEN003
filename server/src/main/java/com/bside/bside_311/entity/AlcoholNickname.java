package com.bside.bside_311.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlcoholNickname extends BaseEntity{
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  @Column(name = "alcohol_nickname_no")
  private Long id;

  @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
  @JoinColumn(name = "alcohol_no")
  private Alcohol alcohol;

  private String nickname;

  @Builder
  public AlcoholNickname(Long id, Alcohol alcohol, String nickname) {
    this.id = id;
    this.alcohol = alcohol;
    this.nickname = nickname;
  }

  public static AlcoholNickname of(String name) {
    return AlcoholNickname.builder()
            .nickname(name)
            .build();
  }

  public static List<AlcoholNickname> of(List<String> names) {
    return names.stream().map(name -> AlcoholNickname.of(name)).collect(Collectors.toList());
  }
}
