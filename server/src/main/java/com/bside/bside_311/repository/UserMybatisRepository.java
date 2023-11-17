package com.bside.bside_311.repository;

import com.bside.bside_311.dto.FindUserMvo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMybatisRepository {
  List<FindUserMvo> getUsers();
}
