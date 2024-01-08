package com.bside.bside_311.component;

import com.bside.bside_311.dto.AttachDto;
import com.bside.bside_311.entity.Attach;
import com.bside.bside_311.entity.AttachType;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.AttachRepository;
import com.bside.bside_311.service.ImageStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bside.bside_311.util.ValidateUtil.resourceChangeableCheckByThisRequestToken;

@Component
@RequiredArgsConstructor
public class AttachManager {
  private final AttachRepository attachRepository;
  private final ImageStorage imageStorage;

  public void deleteAttachByAttachNo(Long attachNo) {
    // search attach
    Attach attach = attachRepository.findByIdAndDelYnIs(attachNo, YesOrNo.N)
                                    .orElseThrow(
                                        () -> new IllegalArgumentException("존재하지 않는 첨부파일입니다."));
    resourceChangeableCheckByThisRequestToken(attach);
    // delete attach
    attach.setDelYn(YesOrNo.Y);

    // delte image
    imageStorage.delete(attach.getPublicId());
  }

  public void deleteAttachesByRefNoAndAttachType(Long refNo, AttachType attachType) {
    // search attach
    List<Attach> attaches =
        attachRepository.findByRefNoAndAttachTypeIsAndDelYnIs(refNo, attachType, YesOrNo.N);
    for (Attach attach : attaches
    ) {
      resourceChangeableCheckByThisRequestToken(attach);
      // delete attach
      attach.setDelYn(YesOrNo.Y);
    }
    attachRepository.saveAll(attaches);


    // delte image
    for (Attach attach : attaches
    ) {
      imageStorage.delete(attach.getPublicId());
    }
  }

  public Map<Long, List<AttachDto>> getAttachInfoMapBykeysAndType(List<Long> keys,
                                                                  AttachType attachType) {
    List<Attach> attachList =
        attachRepository.findByRefNoInAndAttachTypeIsAndDelYnIs(keys, attachType,
            YesOrNo.N);
    Map<Long, List<AttachDto>> kToAMap = new HashMap<>();
    for (Attach attach : attachList) {
      if (!kToAMap.containsKey(attach.getRefNo())) {
        kToAMap.put(attach.getRefNo(), new ArrayList<>());
      }
      List<AttachDto> attachDtos = kToAMap.get(attach.getRefNo());
      attachDtos.add(AttachDto.of(attach));
    }
    return kToAMap;
  }

  public List<AttachDto> getAttachListBykeyAndType(Long key,
                                                   AttachType attachType) {
    List<Attach> attachList =
        attachRepository.findByRefNoInAndAttachTypeIsAndDelYnIs(List.of(key), attachType,
            YesOrNo.N);
    List<AttachDto> attachDtos = new ArrayList<>();
    for (Attach attach : attachList) {
      attachDtos.add(AttachDto.of(attach));
    }
    return attachDtos;
  }
}
