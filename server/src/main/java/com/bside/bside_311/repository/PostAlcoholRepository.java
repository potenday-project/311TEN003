package com.bside.bside_311.repository;

import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.PostAlcohol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostAlcoholRepository extends JpaRepository<PostAlcohol, Long> {
  List<PostAlcohol> findByPost(Post post);
}
