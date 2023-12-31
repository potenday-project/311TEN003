package com.bside.bside_311.repository;

import com.bside.bside_311.dto.UserIncludeFollowCountDto;
import com.bside.bside_311.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {
  Page<User> getMyFollowingUsersPage(Long userNo, Pageable pageable);

  Page<User> getUsersOfFollowingMePage(Long userNo, Pageable pageable);

  Page<UserIncludeFollowCountDto> getUsersPopular(Long page, Long size);
}
