package com.bside.bside_311.exercise;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TestRepository {
//  void insertMember(TestMemberVo memberVo);
    void insertTeam(TestTeamVo teamVo);
}
