package com.bside.bside_311.service;

import com.bside.bside_311.entity.Tag;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TagService {
  private final TagRepository tagRepository;

  public List<Tag> addOrSetTags(List<String> tagStrList) {
    List<Tag> tags = new ArrayList<>();
    if (tagStrList != null) {
      for (String tagName : tagStrList) {
        Tag tag = tagRepository.findByNameAndDelYnIs(tagName, YesOrNo.N).orElse(Tag.of(tagName));
        tagRepository.save(tag);
        tags.add(tag);
      }
    }

    return tags;
  }

  public List<Tag> searchTag(String searchKeyword) {
    return tagRepository.findByNameContainingAndDelYnIs(searchKeyword, YesOrNo.N);
  }
}


