package com.bside.bside_311.repository;

import com.bside.bside_311.entity.PostTag;
import com.bside.bside_311.entity.Tag;
import com.bside.bside_311.entity.YesOrNo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
  //  Optional<Alcohol> findByNameAndDelYnIs(String name, YesOrNo delYn);
//
//  Optional<Alcohol> findByIdAndDelYnIs(Long userNo, YesOrNo delYn);
  Optional<Tag> findByNameAndDelYnIs(String name, YesOrNo delYn);

  List<Tag> findByPostTagsInAndDelYnIs(List<PostTag> postTags, YesOrNo delYn);
}
