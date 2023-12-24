package com.bside.bside_311.entity;

import com.bside.bside_311.dto.AddAlcoholRequestDto;
import com.bside.bside_311.dto.GetAlcoholsMvo;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

import static com.bside.bside_311.util.JsonParseUtil.tastePreProcessing;

@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alcohol extends BaseEntity {

  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  @Column(name = "alcohol_no")
  private Long id;

  @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
  @JoinColumn(name = "alcohol_type_no")
  private AlcoholType alcoholType;

  private String name;

  @Builder.Default
  @OneToMany(mappedBy = "alcohol", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AlcoholNickname> alcoholNicknames = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "alcohol", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostAlcohol> postAlcohols = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "alcohol", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AlcoholTag> alcoholTags = new ArrayList<>();

  private String manufacturer;
  private String description;
  @Column(columnDefinition = "LONGTEXT")
  private String taste;
  private Float degree;
  private Long period;
  private Long productionYear;
  private Long volume;

  public static Alcohol of(AddAlcoholRequestDto addAlcoholRequestDto, AlcoholType alcoholType)
      throws JsonProcessingException {
    Alcohol alcohol = Alcohol.builder()
                             .name(addAlcoholRequestDto.getAlcoholName())
                             .alcoholNicknames(new ArrayList<>())
                             .description(addAlcoholRequestDto.getDescription())
                             .taste(tastePreProcessing(addAlcoholRequestDto.getTaste()))
                             .manufacturer(addAlcoholRequestDto.getManufacturer())
                             .degree(addAlcoholRequestDto.getDegree())
                             .period(addAlcoholRequestDto.getPeriod())
                             .productionYear(addAlcoholRequestDto.getProductionYear())
                             .volume(addAlcoholRequestDto.getVolume())
                             .build();
    alcohol.setAlcoholType(alcoholType);
    AlcoholNickname.of(addAlcoholRequestDto.getNickNames()).forEach(alcohol::addAlcoholNickname);
    return alcohol;
  }


  public static Alcohol of(GetAlcoholsMvo getAlcoholsMvo) {
    Alcohol alcohol = Alcohol.builder()
                             .id(getAlcoholsMvo.getAlcoholNo())
                             .name(getAlcoholsMvo.getName())
                             .alcoholNicknames(new ArrayList<>())
                             .description(getAlcoholsMvo.getDescription())
                             .manufacturer(getAlcoholsMvo.getManufacturer())
                             .degree(getAlcoholsMvo.getDegree())
                             .period(getAlcoholsMvo.getPeriod())
                             .productionYear(getAlcoholsMvo.getProductionYear())
                             .volume(getAlcoholsMvo.getVolume())
                             .build();
    AlcoholNickname.of(getAlcoholsMvo.getNicknames()).forEach(alcohol::addAlcoholNickname);
    return alcohol;
  }

  public static Alcohol of(AddAlcoholRequestDto addAlcoholRequestDto) {
    Alcohol alcohol = Alcohol.builder()
                             .name(addAlcoholRequestDto.getAlcoholName())
                             .alcoholNicknames(new ArrayList<>())
                             .description(addAlcoholRequestDto.getDescription())
                             .manufacturer(addAlcoholRequestDto.getManufacturer())
                             .degree(addAlcoholRequestDto.getDegree())
                             .period(addAlcoholRequestDto.getPeriod())
                             .productionYear(addAlcoholRequestDto.getProductionYear())
                             .volume(addAlcoholRequestDto.getVolume())
                             .build();
    AlcoholNickname.of(addAlcoholRequestDto.getNickNames()).forEach(alcohol::addAlcoholNickname);
    return alcohol;
  }

  public void setAlcoholNicknames(List<AlcoholNickname> alcoholNicknames) {

    this.alcoholNicknames.clear();
    for (AlcoholNickname alcoholNickname : alcoholNicknames) {
      this.addAlcoholNickname(alcoholNickname);
    }
  }

  // 연관관계 편의 메서드.
  public void addAlcoholNickname(AlcoholNickname alcoholNickname) {
    if (!ObjectUtils.isEmpty(alcoholNickname)) {
      this.alcoholNicknames.add(alcoholNickname);
      alcoholNickname.setAlcohol(this);
    }
  }

  public void addPostAlcohol(PostAlcohol postAlcohol) {
    if (!ObjectUtils.isEmpty(postAlcohol)) {
      this.postAlcohols.add(postAlcohol);
      postAlcohol.setAlcohol(this);
    }
  }

  public void addAlcoholTag(AlcoholTag alcoholTag) {
    if (!ObjectUtils.isEmpty(alcoholTag)) {
      this.alcoholTags.add(alcoholTag);
      alcoholTag.setAlcohol(this);
    }
  }

  public void removeAllAlcoholTagsAndAddNewAlcoholTags(List<Tag> tags) {
    List<AlcoholTag> previousAlcoholTags = this.getAlcoholTags();
    for (AlcoholTag alcoholTag : previousAlcoholTags) {
      alcoholTag.setDelYn(YesOrNo.Y);
    }
    tags.forEach(tag -> {
      AlcoholTag alcoholTag = AlcoholTag.of(this, tag);
      this.addAlcoholTag(alcoholTag);
      tag.addAlcoholTag(alcoholTag);
    });
  }
}
