package com.bside.bside_311.controller;

import com.bside.bside_311.dto.ChangePasswordRequestDto;
import com.bside.bside_311.dto.FindUserMvo;
import com.bside.bside_311.dto.GetUserInfoResponseDto;
import com.bside.bside_311.dto.LoginResponseDto;
import com.bside.bside_311.dto.MyInfoResponseDto;
import com.bside.bside_311.dto.UserLoginRequestDto;
import com.bside.bside_311.dto.UserSignupRequestDto;
import com.bside.bside_311.dto.UserSignupResponseDto;
import com.bside.bside_311.dto.UserUpdateRequestDto;
import com.bside.bside_311.entity.User;
import com.bside.bside_311.repository.UserMybatisRepository;
import com.bside.bside_311.service.UserService;
import com.bside.bside_311.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "유저", description = "유저 API")
public class UserController {
  private final UserService userService;
  private final UserMybatisRepository userMybatisRepository;

  @Operation(summary = "유저 조회", description = "유저 조회 API")
  @GetMapping()
  public void getUser() {
    List<FindUserMvo> users = userMybatisRepository.getUsers();
    log.info(">>> UserController.getUser");
  }

  @Operation(summary = "[o]유저 등록", description = "유저 등록 API")
  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  public UserSignupResponseDto signup(
      @Valid @RequestBody UserSignupRequestDto userSignupRequestDto) {
    log.info(">>> UserController.signup");
    return userService.signUp(User.of(userSignupRequestDto));
  }

  @Operation(summary = "[o]유저 로그인", description = "유저 로그인 API")
  @PostMapping("/login")
  public LoginResponseDto login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto) {
    log.info(">>> UserController.login");

    return userService.login(userLoginRequestDto);
  }

  @Operation(summary = "성인 인증 API(추후)")
  @PostMapping("/self-auth")
  public void selfAuth() {
    log.info(">>> UserController.selfAuth");
  }

  @Operation(summary = "이메일 인증 API(추후)")
  @PostMapping("/email/authenticate")
  public void emailAuthenticate() {
    log.info(">>> UserController.emailAuthenticate");
  }

  @Operation(summary = "이메일 인증코드 확인API(추후)")
  @PostMapping("/email/authenticate/valid")
  public void emailAuthenticateValid() {
    log.info(">>> UserController.emailAuthenticateValid");
  }

  @Operation(summary = "임시 비밀번호 전달 API(추후)")
  @PostMapping("/password/temp/send")
  public void passwordTempSend() {
    log.info(">>> UserController.passwordTempSend");
  }

  @Operation(summary = "[o]유저 정보 변경")
  @PatchMapping()
  @PreAuthorize("hasAnyRole('ROLE_USER')")
  public void updateUser(@RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto) {
    Long userNo = AuthUtil.getUserNoFromAuthentication();
    userService.updateUser(userNo, userUpdateRequestDto);
    log.info(">>> UserController.updateUser");
  }

  @Operation(summary = "비밀번호 변경", description = "비밀번호 변경 API")
  @PatchMapping("/pwd/change")
  public void changePassword(
      @RequestBody @Valid ChangePasswordRequestDto changePasswordRequestDto) {
    log.info(">>> UserController.changePassword");
  }

  @Operation(summary = "[~]내 정보 조회", description = "내 정보 조회 API")
  @GetMapping("/me")
  public MyInfoResponseDto getMyInfo() {
    Long myUserNo = AuthUtil.getUserNoFromAuthentication();

    log.info(">>> UserController.getUserInfo");
    return userService.getMyInfo(myUserNo);
  }

  @Operation(summary = "[~]유저 정보 조회", description = "유저 정보 조회 API")
  @GetMapping("/{userNo}/summary")
  public GetUserInfoResponseDto getUserInfo(@PathVariable("userNo") Long userNo) {
    log.info(">>> UserController.getUserInfo");
    return userService.getUserInfo(userNo);
  }

  @Operation(summary = "[o]회원 탈퇴", description = "회원 탈퇴 API")
  @DeleteMapping()
  public void withdraw() {
    Long myUserNo = AuthUtil.getUserNoFromAuthentication();
    userService.withdraw(myUserNo);
    log.info(">>> UserController.withdrawl");
  }

  @Operation(summary = "유저 팔로우하기", description = "유저 팔로우하기 API")
  @PostMapping("/follow/{userNo}")
  void followUser(@PathVariable("userNo") Long userNo) {
    log.info(">>> UserController.followUser");
  }

  @Operation(summary = "유저 언팔로우하기", description = "유저 언팔로우하기 API")
  @PostMapping("/unfollow/{user_no}")
  void unfollowUser(@PathVariable("user_no") Long userNo) {
    log.info(">>> UserController.followUser");
  }

}
