package com.bside.bside_311.repository;

import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.YesOrNo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
  Optional<Post> findByIdAndDelYnIs(Long postNo, YesOrNo delYn);
}
