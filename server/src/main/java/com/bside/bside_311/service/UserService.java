package com.bside.bside_311.service;

import com.bside.bside_311.dto.GetUserInfoResponseDto;
import com.bside.bside_311.dto.LoginResponseDto;
import com.bside.bside_311.dto.MyInfoResponseDto;
import com.bside.bside_311.dto.UserLoginRequestDto;
import com.bside.bside_311.dto.UserSignupResponseDto;
import com.bside.bside_311.dto.UserUpdateRequestDto;
import com.bside.bside_311.entity.User;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.UserRepository;
import com.bside.bside_311.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bside.bside_311.util.JwtUtil.NORMAL_TOKEN;
import static com.bside.bside_311.util.JwtUtil.normalValidity;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {
  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserSignupResponseDto signUp(User user) {
    List<User> users =
        userRepository.findByEmailOrUserIdAndDelYnIs(user.getEmail(), user.getUserId(),
            YesOrNo.N);

    if (users.size() > 0) {
      log.info(">>> UserService.signUp: 중복된 이메일 또는 아이디가 존재합니다.");
      throw new IllegalArgumentException("중복된 이메일 또는 아이디가 존재합니다.");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    return UserSignupResponseDto.of(user.getId());
  }

  public LoginResponseDto login(UserLoginRequestDto userLoginRequestDto) {
    User foundUser = userRepository.findByUserIdAndDelYnIs(userLoginRequestDto.getId(), YesOrNo.N)
                                   .orElseThrow(
                                       () -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), foundUser.getPassword())) {
      throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
    }
    Authentication authentication = new UsernamePasswordAuthenticationToken(foundUser.getId(), null,
        AuthorityUtils.createAuthorityList(foundUser.getRole().toString()));
    return LoginResponseDto.of(
        jwtUtil.createLocalToken(foundUser, NORMAL_TOKEN, normalValidity, authentication));
  }

  public void updateUser(Long userNo, UserUpdateRequestDto userUpdateRequestDto) {
    User user = userRepository.findByIdAndDelYnIs(userNo, YesOrNo.N)
                              .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    if (userUpdateRequestDto != null) {
      if (userUpdateRequestDto.getIntroduction() != null) {
        user.setIntroduction(userUpdateRequestDto.getIntroduction());
      }
    }
    userRepository.save(user);
  }

  public GetUserInfoResponseDto getUserInfo(Long userNo) {
    User user = userRepository.findByIdAndDelYnIs(userNo, YesOrNo.N)
                              .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    //TODO 여기 다른값도 적절히 넣어서 구현 해야함.
//    return GetUserInfoResponseDto.of(user, profileList, count, true);
    return GetUserInfoResponseDto.of(user);
  }

  public MyInfoResponseDto getMyInfo(Long myUserNo) {
    User user = userRepository.findByIdAndDelYnIs(myUserNo, YesOrNo.N)
                              .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    //TODO 여기 다른값도 적절히 넣어서 구현 해야함.
//    return GetUserInfoResponseDto.of(user, profileList, count, true);
    return MyInfoResponseDto.of(user);
  }

  public void withdraw(Long myUserNo) {
    User user = userRepository.findByIdAndDelYnIs(myUserNo, YesOrNo.N)
                              .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    user.setDelYn(YesOrNo.Y);
    userRepository.save(user);
  }


}
