package com.bside.bside_311.repository;

import com.bside.bside_311.dto.GetPostVo;
import com.bside.bside_311.dto.GetPostsMvo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PostMybatisRepository {
  List<GetPostsMvo> getPosts(GetPostVo getPostVo);

  Long getPostsCount(GetPostVo getPostVo);
}
