package com.bside.bside_311.service;

import com.bside.bside_311.dto.AddAlcoholRequestDto;
import com.bside.bside_311.dto.AddAlcoholResponseDto;
import com.bside.bside_311.dto.AlcoholResponseDto;
import com.bside.bside_311.dto.EditAlcoholRequestDto;
import com.bside.bside_311.dto.GetAlcoholResponseDto;
import com.bside.bside_311.dto.GetAlcoholTypesResponseDto;
import com.bside.bside_311.dto.GetAlcoholsMvo;
import com.bside.bside_311.dto.GetAlcoholsVo;
import com.bside.bside_311.entity.Alcohol;
import com.bside.bside_311.entity.AlcoholNickname;
import com.bside.bside_311.entity.AlcoholType;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.AlcoholMybatisRepository;
import com.bside.bside_311.repository.AlcoholNicknameRepository;
import com.bside.bside_311.repository.AlcoholRepository;
import com.bside.bside_311.repository.AlcoholTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AlcoholService {
  private final AlcoholRepository alcoholRepository;
  private final AlcoholTypeRepository alcoholTypeRepository;
  private final AlcoholMybatisRepository alcoholMybatisRepository;
  private final AlcoholNicknameRepository alcoholNicknameRepository;

  public AddAlcoholResponseDto addAlcohol(AddAlcoholRequestDto addAlcoholRequestDto) {
    Long alcoholTypeNo = addAlcoholRequestDto.getAlcoholTypeNo();
    AlcoholType alcoholType = alcoholTypeRepository.findByIdAndDelYnIs(alcoholTypeNo, YesOrNo.N)
                                                   .orElseThrow(
                                                       () -> new IllegalArgumentException(
                                                           "술 종류가 존재하지 않습니다."));
    Alcohol alcohol = Alcohol.of(addAlcoholRequestDto, alcoholType);

    // 이름으로 중복 검색.
    alcoholRepository.findByNameAndDelYnIs(alcohol.getName(), YesOrNo.N).ifPresent(alcohol1 -> {
      log.info(">>> AlcoholService.addAlcohol: 중복된 술 이름이 존재합니다.");
      throw new IllegalArgumentException("중복된 술 이름이 존재합니다.");
    });
    alcoholRepository.save(alcohol);
    return AddAlcoholResponseDto.of(alcohol.getId());
  }

  public void editAlcohol(Long alcoholNo, EditAlcoholRequestDto editAlcoholRequestDto) {
    Alcohol alcohol = alcoholRepository.findByIdAndDelYnIs(alcoholNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException("술이 존재하지 않습니다."));
    if (editAlcoholRequestDto != null) {
      if (editAlcoholRequestDto.getAlcoholName() != null) {
        alcohol.setName(editAlcoholRequestDto.getAlcoholName());
      }
      if (editAlcoholRequestDto.getAlcoholTypeNo() != null) {
        AlcoholType alcoholType = alcoholTypeRepository.findByIdAndDelYnIs(
            editAlcoholRequestDto.getAlcoholTypeNo(), YesOrNo.N).orElseThrow(
            () -> new IllegalArgumentException("술 종류가 존재하지 않습니다."));
        alcohol.setAlcoholType(alcoholType);
      }
      if (editAlcoholRequestDto.getNickNames() != null &&
              editAlcoholRequestDto.getNickNames().size() > 0) {
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
    Alcohol alcohol = alcoholRepository.findByIdAndDelYnIs(alcoholNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException("술이 존재하지 않습니다."));
    alcohol.setDelYn(YesOrNo.Y);
    alcoholRepository.save(alcohol);
  }

  public AlcoholResponseDto getAlcoholDetail(Long alcoholNo) {
    Alcohol alcohol = alcoholRepository.findByIdAndDelYnIs(alcoholNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException("술이 존재하지 않습니다."));
    return AlcoholResponseDto.of(alcohol);
  }

  public GetAlcoholResponseDto getAlcohol(Long page, Long size, String orderColumn,
                                          String orderType, String searchKeyword) {
    // TODO 닉네임 검색 가능하도록 조치.

    // TODO 성능 이슈 조치.
    GetAlcoholsVo getAlcoholsVo =
        GetAlcoholsVo.builder()
                     .page(page)
                     .offset(page * size)
                     .size(size)
                     .orderColumn(orderColumn)
                     .orderType(orderType)
                     .searchKeyword(searchKeyword)
                     .build();
    List<GetAlcoholsMvo> getAlcoholsMvos = alcoholMybatisRepository.getAlcohols(getAlcoholsVo);
    List<Alcohol> alcohols = getAlcoholsMvos.stream().map(Alcohol::of).collect(Collectors.toList());
    Long alcoholsCount = alcoholMybatisRepository.getAlcoholsCount(getAlcoholsVo);
    List<AlcoholNickname> alcoholNicknames =
        alcoholNicknameRepository.findByAlcoholInAndDelYnIs(alcohols, YesOrNo.N);
    Map<Long, List<AlcoholNickname>> alcoholNicknameMap = alcoholNicknames.stream().collect(
        Collectors.groupingBy(alcoholNickname -> alcoholNickname.getAlcohol().getId()));
    for (Alcohol alcohol : alcohols) {
      List<AlcoholNickname> alcoholNicknamesByAlcoholNo =
          alcoholNicknameMap.getOrDefault(alcohol.getId(), new ArrayList<>());
      alcohol.setAlcoholNicknames(alcoholNicknamesByAlcoholNo);
    }
    return GetAlcoholResponseDto.of(alcohols, alcoholsCount);
  }

  public GetAlcoholTypesResponseDto getAlcoholTypes() {
    List<AlcoholType> alcoholTypes = alcoholTypeRepository.findAll();
    return GetAlcoholTypesResponseDto.of(alcoholTypes);
  }
}
