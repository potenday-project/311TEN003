package com.bside.bside_311.entity;

import com.bside.bside_311.dto.AddAlcoholRequestDto;
import com.bside.bside_311.dto.GetAlcoholsMvo;
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
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicInsert
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

  private String manufacturer;
  private String description;
  private float degree;
  private Long period;
  private Long productionYear;
  private Long volume;

  public static Alcohol of(AddAlcoholRequestDto addAlcoholRequestDto, AlcoholType alcoholType) {
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
}
