package com.bside.bside_311.service;

import com.bside.bside_311.dto.UserResponseDto;
import com.bside.bside_311.entity.User;
import com.bside.bside_311.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
class UserServiceTest {
  @Autowired
  private UserService userService;

  @MockBean
  private UserRepository userRepository;

  @Test
  void getMyFollowingUsers() {
    // given
    int limit = 3;

    Sort sort = Sort.by(Sort.Direction.DESC, "username");
    PageRequest pageRequest = PageRequest.of(0, limit, sort);
    Page<User> pagedResponse = new PageImpl<>(List.of(User.of(1L)));
    given(userRepository.getMyFollowingUsersPage(eq(1L), any())).willReturn(pagedResponse);
    // when
    Page<UserResponseDto> myFollowingUsers = userService.getMyFollowingUsers(1L, pageRequest);
    // then
    Assertions.assertThat(myFollowingUsers.getContent().size()).isEqualTo(1);
  }

  @Test
  void getUsersOfFollowingMePage() {
    // given
    int limit = 3;

    Sort sort = Sort.by(Sort.Direction.DESC, "username");
    PageRequest pageRequest = PageRequest.of(0, limit, sort);
    Page<User> pagedResponse = new PageImpl<>(List.of(User.of(1L)));
    given(userRepository.getUsersOfFollowingMePage(eq(1L), any())).willReturn(pagedResponse);
    // when
    Page<UserResponseDto> myFollowingUsers = userService.getUsersOfFollowingMe(1L, pageRequest);
    // then
    Assertions.assertThat(myFollowingUsers.getContent().size()).isEqualTo(1);
  }
}