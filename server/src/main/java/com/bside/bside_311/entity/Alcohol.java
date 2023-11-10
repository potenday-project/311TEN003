package com.bside.bside_311.entity;

import com.bside.bside_311.dto.AddAlcoholRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alcohol extends BaseEntity{

  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  @Column(name = "alcohol_no")
  private Long id;

  private String name;

  @OneToMany(mappedBy = "alcohol", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AlcoholNickname> alcoholNicknames = new ArrayList<>();

  private String manufacturer;
  private String description;
  private float degree;
  private Integer period;
  private Integer productionYear;
  private Integer volume;

  public void setAlcoholNicknames(List<AlcoholNickname> alcoholNicknames) {

    this.alcoholNicknames.clear();
    for (AlcoholNickname alcoholNickname : alcoholNicknames) {
      this.addAlcoholNickname(alcoholNickname);
    }
  }

  @Builder
  public Alcohol(Long id, String name, List<AlcoholNickname> alcoholNicknames, String manufacturer,
                 String description, float degree, Integer period, Integer productionYear,
                 Integer volume) {
    this.id = id;
    this.name = name;
    this.alcoholNicknames = alcoholNicknames;
    this.manufacturer = manufacturer;
    this.description = description;
    this.degree = degree;
    this.period = period;
    this.productionYear = productionYear;
    this.volume = volume;
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

  // 연관관계 편의 메서드.
  public void addAlcoholNickname(AlcoholNickname alcoholNickname){
    this.alcoholNicknames.add(alcoholNickname);
    alcoholNickname.setAlcohol(this);
  }
}
