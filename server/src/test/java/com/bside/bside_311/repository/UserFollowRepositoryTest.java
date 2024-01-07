package com.bside.bside_311.repository;

import com.bside.bside_311.entity.UserFollow;
import com.bside.bside_311.entity.YesOrNo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserFollowRepositoryTest {
  @Autowired
  private UserFollowRepository userFollowRepository;

  @Test
  void findByFollowing_IdAndDelYnIs() {
    //g
    //w
    List<UserFollow> byFollowing_idAndDelYnIs =
        userFollowRepository.findByFollowing_IdAndDelYnIs(1L, YesOrNo.N);
    //t
    System.out.println("byFollowing_idAndDelYnIs = " + byFollowing_idAndDelYnIs);
  }
}