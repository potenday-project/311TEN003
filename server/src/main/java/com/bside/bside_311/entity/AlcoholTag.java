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
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlcoholTag extends BaseEntity {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  @Column(name = "post_tag_no")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "alcohol_no")
  private Alcohol alcohol;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tag_no")
  private Tag tag;

  @Builder
  public AlcoholTag(Long id, Alcohol alcohol, Tag tag) {
    this.id = id;
    this.alcohol = alcohol;
    this.tag = tag;
  }

  public static AlcoholTag of(Alcohol alcohol, Tag tag) {
    if (alcohol == null || tag == null) {
      return null;
    }
    return AlcoholTag.builder()
                     .alcohol(alcohol)
                     .tag(tag)
                     .build();
  }
}
