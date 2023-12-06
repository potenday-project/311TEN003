package com.bside.bside_311.repository;

import com.bside.bside_311.entity.User;
import com.bside.bside_311.entity.UserFollow;
import com.bside.bside_311.entity.YesOrNo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
//@Rollback(false)
class UserRepositoryTest {
  @Autowired
  UserRepository userRepository;
  @Autowired
  UserFollowRepository userFollowRepository;
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

  @Test
  public void findAllByIdInAndDelYnIs2() {
    // given
    // when
    Optional<User> byIdAndDelYnIs = userRepository.findByIdAndDelYnIs(1L, YesOrNo.N);
    // then
    Assertions.assertThat(byIdAndDelYnIs).isEmpty();
  }

  @Test
  public void getMyFollowingUsersPage_success() {
    // given
    int limit = 300;

    Sort sort = Sort.by(Sort.Direction.DESC, "id");
    PageRequest pageRequest = PageRequest.of(0, limit, sort);

    //
    List<User> userList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      User test = User.builder().id(i + 1L)
                      .userId(String.format("test%d", i))
                      .nickname(String.format("test%d", i))
                      .password("test1!").build();
      userList.add(test);
      userRepository.save(test);
    }

    em.flush();
    // 1L -> 2L follow
    userFollowRepository.save(
        UserFollow.of(userList.get(0), userList.get(1))
    );

    // 1L -> 3L follow
    userFollowRepository.save(
        UserFollow.of(userList.get(0), userList.get(2))
    );

    // 3L -> 4L follow
    userFollowRepository.save(
        UserFollow.of(userList.get(2), userList.get(3))
    );


    // when
    Page<User> myFollowingUsersPage = userRepository.getMyFollowingUsersPage(1L, pageRequest);
    // then
    Assertions.assertThat(myFollowingUsersPage.getContent().size()).isEqualTo(2);
  }

  @Test
  public void getUsersOfFollowingMePage_success() {
    // given
    int limit = 300;

    Sort sort = Sort.by(Sort.Direction.DESC, "id");
    PageRequest pageRequest = PageRequest.of(0, limit, sort);

    //
    List<User> userList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      User test = User.builder().id(i + 1L)
                      .userId(String.format("test%d", i))
                      .nickname(String.format("test%d", i))
                      .password("test1!").build();
      userList.add(test);
      userRepository.save(test);
    }

    em.flush();
    // 1L -> 2L follow
    userFollowRepository.save(
        UserFollow.of(userList.get(0), userList.get(1))
    );

    // 1L -> 3L follow
    userFollowRepository.save(
        UserFollow.of(userList.get(0), userList.get(2))
    );

    // 3L -> 4L follow
    userFollowRepository.save(
        UserFollow.of(userList.get(2), userList.get(3))
    );

    // 2L -> 4L follow
    userFollowRepository.save(
        UserFollow.of(userList.get(1), userList.get(3))
    );


    // when
    Page<User> myFollowingUsersPage = userRepository.getUsersOfFollowingMePage(4L, pageRequest);
    // then
    Assertions.assertThat(myFollowingUsersPage.getContent().size()).isEqualTo(2);
  }

}