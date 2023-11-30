package com.bside.bside_311.repository;

import com.bside.bside_311.entity.User;
import com.bside.bside_311.entity.YesOrNo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class UserRepositoryTest {
  @Autowired
  UserRepository userRepository;
  @PersistenceContext
  private EntityManager em;

  @Test
  public void findAllByIdInAndDelYnIs() {
    // given
    // when
    List<User> allByIdInAndDelYnIs = userRepository.findAllByIdInAndDelYnIs(null, YesOrNo.N);
    // then
    allByIdInAndDelYnIs.forEach(System.out::println);
  }

}