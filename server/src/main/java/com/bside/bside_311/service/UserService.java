package com.bside.bside_311.service;

import com.bside.bside_311.dto.AttachDto;
import com.bside.bside_311.dto.ChangePasswordRequestDto;
import com.bside.bside_311.dto.GetUserInfoResponseDto;
import com.bside.bside_311.dto.LoginResponseDto;
import com.bside.bside_311.dto.MyInfoResponseDto;
import com.bside.bside_311.dto.UserLoginRequestDto;
import com.bside.bside_311.dto.UserResponseDto;
import com.bside.bside_311.dto.UserSignupResponseDto;
import com.bside.bside_311.dto.UserUpdateRequestDto;
import com.bside.bside_311.entity.Attach;
import com.bside.bside_311.entity.AttachType;
import com.bside.bside_311.entity.User;
import com.bside.bside_311.entity.UserFollow;
import com.bside.bside_311.entity.YesOrNo;
import com.bside.bside_311.repository.AttachRepository;
import com.bside.bside_311.repository.UserFollowRepository;
import com.bside.bside_311.repository.UserRepository;
import com.bside.bside_311.util.AuthUtil;
import com.bside.bside_311.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bside.bside_311.util.JwtUtil.NORMAL_TOKEN;
import static com.bside.bside_311.util.JwtUtil.normalValidity;
import static com.bside.bside_311.util.ValidateUtil.resourceChangeableCheckByThisRequestToken;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {
  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserFollowRepository userFollowRepository;
  private final AttachRepository attachRepository;

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
    // resourceChangeableCheckByThisRequestToken(user);
    // 리소스 변경 예외. 유저 생성시에는  createdBy정보가 없음.
    if (userUpdateRequestDto != null) {
      if (userUpdateRequestDto.getIntroduction() != null) {
        user.setIntroduction(userUpdateRequestDto.getIntroduction());
      }
      if (userUpdateRequestDto.getNickname() != null) {
        user.setNickname(userUpdateRequestDto.getNickname());
      }
    }
    userRepository.save(user);
  }

  public GetUserInfoResponseDto getUserInfo(Long userNo) {
    User user = userRepository.findByIdAndDelYnIs(userNo, YesOrNo.N)
                              .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    List<Attach> profileAttachList =
        attachRepository.findByRefNoAndAttachTypeIsAndDelYnIs(userNo, AttachType.PROFILE,
            YesOrNo.N);
    Long followerCount =
        userFollowRepository.countByFollowedAndDelYnIs(user.of(userNo), YesOrNo.N);
    Long followingCount =
        userFollowRepository.countByFollowingAndDelYnIs(user.of(userNo), YesOrNo.N);
    Boolean isFollowing = null;
    Long myUserNo = AuthUtil.getUserNoFromAuthentication();
    if (myUserNo != null) {
      isFollowing = userFollowRepository.findByFollowingAndFollowedAndDelYnIs(user.of(myUserNo),
                                            user.of(userNo), YesOrNo.N)
                                        .isPresent();
    }

    return GetUserInfoResponseDto.of(
        MyInfoResponseDto.of(user, AttachDto.of(profileAttachList), followerCount, followingCount),
        isFollowing);
  }

  public MyInfoResponseDto getMyInfo(Long myUserNo) {
    User user = userRepository.findByIdAndDelYnIs(myUserNo, YesOrNo.N)
                              .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    List<Attach> profileAttachList =
        attachRepository.findByRefNoAndAttachTypeIsAndDelYnIs(myUserNo, AttachType.PROFILE,
            YesOrNo.N);
    Long followerCount =
        userFollowRepository.countByFollowedAndDelYnIs(user.of(myUserNo), YesOrNo.N);
    Long followingCount =
        userFollowRepository.countByFollowingAndDelYnIs(user.of(myUserNo), YesOrNo.N);
    return MyInfoResponseDto.of(user, AttachDto.of(profileAttachList), followerCount,
        followingCount);
  }

  public void withdraw(Long myUserNo) {
    User user = userRepository.findByIdAndDelYnIs(myUserNo, YesOrNo.N)
                              .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    // resourceChangeableCheckByThisRequestToken(user);
    // 리소스 변경 예외. 유저 생성시에는  createdBy정보가 없음.
    user.setDelYn(YesOrNo.Y);
    userRepository.save(user);
  }


  public void chagePoassword(ChangePasswordRequestDto changePasswordRequestDto) {
    Long myUserNo = AuthUtil.getUserNoFromAuthentication();
    if (ObjectUtils.isEmpty(myUserNo)) {
      throw new IllegalArgumentException("로그인이 유효하지 않습니다.");
    }
    User me = userRepository.findByIdAndDelYnIs(myUserNo, YesOrNo.N)
                            .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    //    resourceChangeableCheckByThisRequestToken(me);
    // 리소스 변경 예외. 유저 생성시에는  createdBy정보가 없음.
    if (!passwordEncoder.matches(changePasswordRequestDto.getPassword(), me.getPassword())) {
      throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
    }
    me.setPassword(passwordEncoder.encode(changePasswordRequestDto.getNewPassword()));
    return;
  }

  public UserFollow followUser(Long myUserNo, Long followingUserNo) {
    User me = userRepository.findByIdAndDelYnIs(myUserNo, YesOrNo.N)
                            .orElseThrow(() -> new IllegalArgumentException("로그인 유저가 존재하지 않습니다."));

    User followingUser = userRepository.findByIdAndDelYnIs(followingUserNo, YesOrNo.N)
                                       .orElseThrow(() -> new IllegalArgumentException(
                                           "팔로워하는 유저가 존재하지 않습니다."));
    UserFollow userFollow =
        userFollowRepository.findByFollowingAndFollowedAndDelYnIs(me, followingUser, YesOrNo.N)
                            .orElse(UserFollow.of(me, followingUser));
    userFollowRepository.save(userFollow);
    return userFollow;
  }

  public void unfollowUser(Long myUserNo, Long followingUserNo) {
    User me = userRepository.findByIdAndDelYnIs(myUserNo, YesOrNo.N)
                            .orElseThrow(() -> new IllegalArgumentException("로그인 유저가 존재하지 않습니다."));

    User followingUser = userRepository.findByIdAndDelYnIs(followingUserNo, YesOrNo.N)
                                       .orElseThrow(() -> new IllegalArgumentException(
                                           "팔로워하는 유저가 존재하지 않습니다."));
    UserFollow userFollow =
        userFollowRepository.findByFollowingAndFollowedAndDelYnIs(me, followingUser, YesOrNo.N)
                            .orElseThrow(() -> new IllegalArgumentException("팔로우하지 않은 유저입니다."));
    resourceChangeableCheckByThisRequestToken(userFollow);
    userFollow.setDelYn(YesOrNo.Y);
  }

  public Page<UserResponseDto> getMyFollowingUsers(Long myUserNo, Pageable pageable) {
    Page<User> users = userRepository.getMyFollowingUsersPage(myUserNo, pageable);
    List<Long> userNos = users.stream().map(User::getId).toList();
    Map<Long, List<AttachDto>> uToAMap = getUserAttachInfos(userNos);

    return users.map(user -> {
      List<AttachDto> attachDtos = uToAMap.getOrDefault(user.getId(), List.of());
      // isFollowedByMe는 true로 고정
      return UserResponseDto.of(user, attachDtos, true);
    });
  }

  public Page<UserResponseDto> getUsersOfFollowingMe(Long myUserNo, Pageable pageable) {
    Page<User> users = userRepository.getUsersOfFollowingMePage(myUserNo, pageable);
    List<Long> userNos = users.stream().map(User::getId).toList();
    List<User> userList = users.stream().toList();
    Map<Long, List<UserFollow>> uToUFMap =
        getUserFollowInfoFollowingIsAndFollowedIsIn(myUserNo, userList);
    Map<Long, List<AttachDto>> uToAMap = getUserAttachInfos(userNos);
    return users.map(user -> {
      List<AttachDto> attachDtos = uToAMap.getOrDefault(user.getId(), List.of());
      Boolean isFollowedByMe = uToUFMap.containsKey(user.getId());
      return UserResponseDto.of(user, attachDtos, isFollowedByMe);
    });
  }

  public Map<Long, List<UserFollow>> getUserFollowInfoFollowingIsAndFollowedIsIn(Long myUserNo,
                                                                                 List<User> followedUserList) {
    List<UserFollow> userFollowList =
        userFollowRepository.findByFollowingIsAndFollowedIsInAndDelYnIs(User.of(myUserNo),
            followedUserList,
            YesOrNo.N);
    Map<Long, List<UserFollow>> uToFMap = new HashMap<>();
    for (UserFollow userFollow : userFollowList) {
      Long targetUserNo = userFollow.getFollowed().getId();
      if (!uToFMap.containsKey(targetUserNo)) {
        uToFMap.put(targetUserNo, new ArrayList<>());
      }
      List<UserFollow> userFollows = uToFMap.get(targetUserNo);
      userFollows.add(userFollow);
    }
    return uToFMap;
  }

  public Map<Long, List<AttachDto>> getUserAttachInfos(List<Long> userNos) {

    List<Attach> userAttachList =
        attachRepository.findByRefNoInAndAttachTypeIsAndDelYnIs(userNos, AttachType.PROFILE,
            YesOrNo.N);
    Map<Long, List<AttachDto>> uToAMap = new HashMap<>();
    for (Attach attach : userAttachList) {
      if (!uToAMap.containsKey(attach.getRefNo())) {
        uToAMap.put(attach.getRefNo(), new ArrayList<>());
      }
      List<AttachDto> attachDtos = uToAMap.get(attach.getRefNo());
      attachDtos.add(AttachDto.of(attach));
    }
    return uToAMap;
  }
}
