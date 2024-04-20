package com.bside.bside_311.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.bside.bside_311.dto.GetPostVo;
import com.bside.bside_311.dto.GetPostsMvo;
import com.bside.bside_311.dto.GetPostsToOneMvo;

@Mapper
@Repository
public interface PostMybatisRepository {
	List<GetPostsMvo> getPosts(GetPostVo getPostVo);

	Long getPostsCount(GetPostVo getPostVo);

	List<GetPostsToOneMvo> getPostsToOne(List<Long> postNos);
}
