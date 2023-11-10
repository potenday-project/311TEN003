package com.bside.bside_311.service;

import com.bside.bside_311.dto.AddAlcoholResponseDto;
import com.bside.bside_311.dto.AlcoholResponseDto;
import com.bside.bside_311.dto.EditAlcoholRequestDto;
import com.bside.bside_311.dto.GetAlcoholResponseDto;
import com.bside.bside_311.dto.GetAlcoholsMvo;
import com.bside.bside_311.dto.GetAlcoholsVo;
import com.bside.bside_311.entity.Alcohol;
import com.bside.bside_311.entity.AlcoholNickname;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.AlcoholMybatisRepository;
import com.bside.bside_311.repository.AlcoholRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlcoholService {
  private final AlcoholRepository alcoholRepository;
  private final AlcoholMybatisRepository alcoholMybatisRepository;
  public AddAlcoholResponseDto addAlcohol(Alcohol alcohol) {
    // 이름으로 중복 검색.
    alcoholRepository.findByNameAndDelYnIs(alcohol.getName(), YesOrNo.N)
                     .ifPresent(alcohol1 -> {
                       log.info(">>> AlcoholService.addAlcohol: 중복된 술 이름이 존재합니다.");
                       throw new IllegalArgumentException("중복된 술 이름이 존재합니다.");
                     });
    alcoholRepository.save(alcohol);
    return AddAlcoholResponseDto.of(alcohol.getId());
  }

  public void editAlcohol(Long alcoholNo, EditAlcoholRequestDto editAlcoholRequestDto) {
    Alcohol alcohol = alcoholRepository.findByIdAndDelYnIs(alcoholNo, YesOrNo.N).orElseThrow(() -> new IllegalArgumentException("술이 존재하지 않습니다."));
    if (editAlcoholRequestDto != null) {
      if (editAlcoholRequestDto.getAlcoholName() != null) {
        alcohol.setName(editAlcoholRequestDto.getAlcoholName());
      }
      if (editAlcoholRequestDto.getNickNames() != null && editAlcoholRequestDto.getNickNames().size() > 0) {
        alcohol.setAlcoholNicknames(AlcoholNickname.of(editAlcoholRequestDto.getNickNames()));
      }
      if (editAlcoholRequestDto.getManufacturer() != null) {
        alcohol.setManufacturer(editAlcoholRequestDto.getManufacturer());
      }
      if (editAlcoholRequestDto.getDescription() != null) {
        alcohol.setDescription(editAlcoholRequestDto.getDescription());
      }
      if (editAlcoholRequestDto.getDegree() != null) {
        alcohol.setDegree(editAlcoholRequestDto.getDegree());
      }
      if (editAlcoholRequestDto.getPeriod() != null) {
        alcohol.setPeriod(editAlcoholRequestDto.getPeriod());
      }
      if (editAlcoholRequestDto.getProductionYear() != null) {
        alcohol.setProductionYear(editAlcoholRequestDto.getProductionYear());
      }
      if (editAlcoholRequestDto.getVolume() != null) {
        alcohol.setVolume(editAlcoholRequestDto.getVolume());
      }
    }

    alcoholRepository.save(alcohol);
  }

  public void deleteAlcohol(Long alcoholNo) {
    Alcohol alcohol = alcoholRepository.findByIdAndDelYnIs(alcoholNo, YesOrNo.N).orElseThrow(() -> new IllegalArgumentException("술이 존재하지 않습니다."));
    alcohol.setDelYn(YesOrNo.Y);
    alcoholRepository.save(alcohol);
  }

  public AlcoholResponseDto getAlcoholDetail(Long alcoholNo) {
    Alcohol alcohol = alcoholRepository.findByIdAndDelYnIs(alcoholNo, YesOrNo.N).orElseThrow(() -> new IllegalArgumentException("술이 존재하지 않습니다."));
    return AlcoholResponseDto.of(alcohol);
  }

  public GetAlcoholResponseDto getAlcohol(Integer page, Integer size, String orderColumn, String orderType, String searchAlcoholKeyword) {
    List<GetAlcoholsMvo> alcohols = alcoholMybatisRepository.getAlcohols(GetAlcoholsVo.builder()
                                                                                      .page(page)
                                                                                      .size(size)
                                                                                      .orderColumn(
                                                                                          orderColumn)
                                                                                      .orderType(
                                                                                          orderType)
                                                                                      .searchAlcoholKeyword(
                                                                                          searchAlcoholKeyword)
                                                                                      .build());
//    alcohols.stream().map(GetAlcoholsMvo::get)
    return null;
  }
}
