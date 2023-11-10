package com.bside.bside_311.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

@Entity
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostAlcohol extends BaseEntity {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  @Column(name = "post_alcohol_no")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_no")
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "alcohol_no")
  private Alcohol alcohol;

  private String alcoholFeature;

  @Builder
  public PostAlcohol(Long id, Post post, Alcohol alcohol, String alcoholFeature) {
    this.id = id;
    this.post = post;
    this.alcohol = alcohol;
    this.alcoholFeature = alcoholFeature;
  }

  public static PostAlcohol of(Post post, Alcohol alcohol, String alcoholFeature) {
    return PostAlcohol.builder().post(post)
                      .alcohol(alcohol)
                      .alcoholFeature(alcoholFeature)
                      .build();
  }
}
