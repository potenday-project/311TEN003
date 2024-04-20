package com.bside.bside_311.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.bside.bside_311.dto.GetAlcoholsMvo;
import com.bside.bside_311.dto.GetAlcoholsVo;

@Mapper
@Repository
public interface AlcoholMybatisRepository {
	List<GetAlcoholsMvo> getAlcohols(GetAlcoholsVo build);

	Long getAlcoholsCount(GetAlcoholsVo build);
}
