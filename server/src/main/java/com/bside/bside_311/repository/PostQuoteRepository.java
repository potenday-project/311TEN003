package com.bside.bside_311.repository;

import com.bside.bside_311.entity.Post;
import com.bside.bside_311.entity.PostQuote;
import com.bside.bside_311.entity.YesOrNo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostQuoteRepository extends JpaRepository<PostQuote, Long> {
  Optional<PostQuote> findByIdAndDelYnIs(Long postQuoteNo, YesOrNo delYn);

  Optional<PostQuote> findByPostAndQuoteAndDelYnIs(Post post, Post quote, YesOrNo delYn);

  List<PostQuote> findByPostAndDelYnIs(Post post, YesOrNo delYn);

  Long countByPostAndDelYnIs(Post post, YesOrNo n);
}
