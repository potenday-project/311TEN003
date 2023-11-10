package com.bside.bside_311.repository;

import com.bside.bside_311.dto.GetAlcoholsMvo;
import com.bside.bside_311.dto.GetAlcoholsVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AlcoholMybatisRepository {
  List<GetAlcoholsMvo> getAlcohols(GetAlcoholsVo build);
}
