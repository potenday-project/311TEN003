package com.bside.bside_311.service;

import com.bside.bside_311.dto.UserSignupResponseDto;
import com.bside.bside_311.entity.User;
import com.bside.bside_311.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  public UserSignupResponseDto signUp(User user) {
    userRepository.save(user);
    return UserSignupResponseDto.of(user.getUserNo());
  }
}
