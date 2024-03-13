package com.bside.bside_311.service;

import com.bside.bside_311.component.AttachManager;
import com.bside.bside_311.dto.ImageRequestDto;
import com.bside.bside_311.dto.UploadImageResultDto;
import com.bside.bside_311.entity.Attach;
import com.bside.bside_311.entity.AttachType;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.model.AbstractUserAuthInfo;
import com.bside.bside_311.repository.AlcoholRepository;
import com.bside.bside_311.repository.AttachRepository;
import com.bside.bside_311.repository.PostRepository;
import com.bside.bside_311.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AttachService {
  private final AttachManager attachManager;
  private final UserRepository userRepository;
  private final PostRepository postRepository;
  private final AlcoholRepository alcoholRepository;
  private final AttachRepository attachRepository;
  private final ImageStorage imageStorage;

  public Attach uploadAttach(ImageRequestDto imageRequestDto, Long resourceNo,
                             AttachType attachTyoe)
      throws IOException {
    MultipartFile image = imageRequestDto.getImage();
    if (image == null || image.isEmpty()) {
      throw new IllegalArgumentException("이미지가 없습니다.");
    }

    // TODO : content
    // search Resouce
    switch (attachTyoe) {
      case POST -> {
        postRepository.findByIdAndDelYnIs(resourceNo, YesOrNo.N)
                      .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
      }
      case PROFILE -> {
        userRepository.findByIdAndDelYnIs(resourceNo, YesOrNo.N)
                      .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
      }
      case ALCOHOL -> {
        alcoholRepository.findByIdAndDelYnIs(resourceNo, YesOrNo.N)
                         .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 술입니다."));
      }
    }
    // insert attach.
    Attach attach = attachRepository.save(
        Attach.of(image, resourceNo, attachTyoe));

    // upload image
    UploadImageResultDto uploadImageResultDto = imageStorage.save(image.getBytes());

    // update attach (cloudnary info)
    attach.setCloudnaryInfo(uploadImageResultDto.getAttachUrl(),
        uploadImageResultDto.getPublicId());

    //return.
    return attach;
  }

  public void deleteAttach(Long attachNo, AbstractUserAuthInfo accessUserAuth) {
    attachManager.deleteAttachByAttachNo(attachNo, accessUserAuth);
  }

}
