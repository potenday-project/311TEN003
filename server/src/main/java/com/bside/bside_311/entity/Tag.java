package com.bside.bside_311.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseEntity {

  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  @Column(name = "tag_no")
  private Long id;

  private String name;

  @Builder.Default
  @OneToMany(mappedBy = "tag")
  private List<PostTag> postTags = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "tag")
  private List<AlcoholTag> alcoholTags = new ArrayList<>();


  public static Tag of(String name) {
    return Tag.builder().name(name).build();
  }

  // 연관관계 편의 메서드.
  public void addPostTag(PostTag postTag) {
    if (!ObjectUtils.isEmpty(postTag)) {
      postTags.add(postTag);
      postTag.setTag(this);
    }
  }

  public void addAlcoholTag(AlcoholTag alcoholTag) {
    if (!ObjectUtils.isEmpty(alcoholTag)) {
      alcoholTags.add(alcoholTag);
      alcoholTag.setTag(this);
    }
  }

}
