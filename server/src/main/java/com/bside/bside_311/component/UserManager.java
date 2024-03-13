package com.bside.bside_311.component;

import com.bside.bside_311.entity.User;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.UserRepository;
import com.bside.bside_311.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserManager {
  private final UserRepository userRepository;

  public User getUser(Long userNo) {
    if (ObjectUtils.isEmpty(userNo)) {
      return null;
    }
    return userRepository.findByIdAndDelYnIs(userNo, YesOrNo.N).orElseThrow(
        () -> new IllegalArgumentException(MessageUtil.USER_NOT_FOUND_MSG));
  }

  public List<User> findUsers(List<Long> commentCreatedList) {
    return userRepository.findAllByIdInAndDelYnIs(commentCreatedList, YesOrNo.N);
  }

}
