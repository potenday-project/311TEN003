package com.bside.bside_311.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.bside.bside_311.dto.FindUserMvo;

@Mapper
@Repository
public interface UserMybatisRepository {
	List<FindUserMvo> getUsers();
}
