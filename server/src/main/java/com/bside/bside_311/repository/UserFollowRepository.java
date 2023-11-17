package com.bside.bside_311.repository;

import com.bside.bside_311.entity.User;
import com.bside.bside_311.entity.UserFollow;
import com.bside.bside_311.entity.YesOrNo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {
  Optional<UserFollow> findByFollowingAndFollowedAndDelYnIs(User following, User followed,
                                                            YesOrNo delYn);

  Long countByFollowedAndDelYnIs(User followed, YesOrNo delYn);

  Long countByFollowingAndDelYnIs(User following, YesOrNo delYn);
}
