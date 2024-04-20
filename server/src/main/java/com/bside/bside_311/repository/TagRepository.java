package com.bside.bside_311.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bside.bside_311.entity.AlcoholTag;
import com.bside.bside_311.entity.PostTag;
import com.bside.bside_311.entity.Tag;
import com.bside.bside_311.entity.YesOrNo;

public interface TagRepository extends JpaRepository<Tag, Long> {
	Optional<Tag> findByNameAndDelYnIs(String name, YesOrNo delYn);

	List<Tag> findByPostTagsInAndDelYnIs(List<PostTag> postTags, YesOrNo delYn);

	List<Tag> findByAlcoholTagsInAndDelYnIs(List<AlcoholTag> alcoholTags, YesOrNo n);

	List<Tag> findByNameContainingAndDelYnIs(String searchKeyword, YesOrNo n);
}
