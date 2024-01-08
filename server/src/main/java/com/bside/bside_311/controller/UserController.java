package com.bside.bside_311.controller;

import com.bside.bside_311.config.security.UserRequired;
import com.bside.bside_311.dto.ChangePasswordRequestDto;
import com.bside.bside_311.dto.GetUserInfoResponseDto;
import com.bside.bside_311.dto.LoginResponseDto;
import com.bside.bside_311.dto.MyInfoResponseDto;
import com.bside.bside_311.dto.UserFollowResponseDto;
import com.bside.bside_311.dto.UserLoginRequestDto;
import com.bside.bside_311.dto.UserResponseDto;
import com.bside.bside_311.dto.UserSignupRequestDto;
import com.bside.bside_311.dto.UserSignupResponseDto;
import com.bside.bside_311.dto.UserUpdateRequestDto;
import com.bside.bside_311.dto.common.ResultDto;
import com.bside.bside_311.entity.Role;
import com.bside.bside_311.entity.User;
import com.bside.bside_311.service.UserService;
import com.bside.bside_311.util.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "유저", description = "유저 API")
public class UserController {
  private final UserService userService;

  @Operation(summary = "[o]일반 유저 등록", description = "일반 유저 등록 API")
  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.CREATED)
  public UserSignupResponseDto signup(
      @Valid @RequestBody UserSignupRequestDto userSignupRequestDto) {
    log.info(">>> UserController.signup");
    return userService.signUp(User.of(userSignupRequestDto, Role.ROLE_USER));
  }

  @Operation(summary = "[o] 어드민 유저 등록", description = "어드민 유저 등록 API")
  @PostMapping("/signup/admin-special")
  @ResponseStatus(HttpStatus.CREATED)
  public UserSignupResponseDto signupAdmin(
      @Valid @RequestBody UserSignupRequestDto userSignupRequestDto) {
    log.info(">>> UserController.signupAdmin");
    return userService.signUp(User.of(userSignupRequestDto, Role.ROLE_ADMIN));
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
  @UserRequired
  public void updateUser(@RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto) {
    Long userNo = AuthUtil.getUserNoFromAuthentication();
    userService.updateUser(userNo, userUpdateRequestDto);
    log.info(">>> UserController.updateUser");
  }

  @Operation(summary = "[o]비밀번호 변경", description = "비밀번호 변경 API")
  @PatchMapping("/pwd/change")
  @UserRequired
  public void changePassword(
      @RequestBody @Valid ChangePasswordRequestDto changePasswordRequestDto) {
    log.info(">>> UserController.changePassword");
    userService.chagePoassword(changePasswordRequestDto);
  }

  @Operation(summary = "[o]내 정보 조회", description = "내 정보 조회 API")
  @GetMapping("/me")
  @UserRequired
  public MyInfoResponseDto getMyInfo() {
    Long myUserNo = AuthUtil.getUserNoFromAuthentication();

    log.info(">>> UserController.getUserInfo");
    return userService.getMyInfo(myUserNo);
  }

  @Operation(summary = "[o]내가 팔로잉하는 유저 조회", description = "내가 팔로잉하는 유저 정보 조회 API")
  @UserRequired
  @GetMapping("/my-following-users")
  public Page<UserResponseDto> getMyFollowingUsers(Pageable pageable) {
    Long myUserNo = AuthUtil.getUserNoFromAuthentication();
    log.info(">>> UserController.getMyFollowingUsers");
    return userService.getMyFollowingUsers(myUserNo, pageable);
  }

  @Operation(summary = "[o]나를 팔로잉하는 유저 조회", description = "나를 팔로잉하는 유저 조회")
  @UserRequired
  @GetMapping("/users-of-following-me")
  public Page<UserResponseDto> getUsersPopular(Pageable pageable) {
    Long myUserNo = AuthUtil.getUserNoFromAuthentication();
    log.info(">>> UserController.getMyFollowingUsers");
    return userService.getUsersOfFollowingMe(myUserNo, pageable);
  }


  // TODO 추후 캐싱 처리 예정.(레디스)
  @Operation(summary = "[o]인기순 유저 조회", description = "인기순 유저 조회")
  @UserRequired
  @GetMapping("/popular")
  public ResultDto<Page<UserResponseDto>> getUsersOfFollowingMe(
      @RequestParam(required = true, name = "page")
      @Schema(description = "페이지", example = "0")
      Long page,
      @RequestParam(required = true, name = "size")
      @Schema(description = "사이즈", example = "10")
      Long size) {
    log.info(">>> UserController.getMyFollowingUsers");
    Long myUserNo = AuthUtil.getUserNoFromAuthentication();
    return userService.getUsersPopular(page, size, myUserNo);
  }

  @Operation(summary = "[o]유저 정보 조회", description = "유저 정보 조회 API")
  @GetMapping("/{userNo}/summary")
  public GetUserInfoResponseDto getUserInfo(@PathVariable("userNo") Long userNo) {
    log.info(">>> UserController.getUserInfo");
    return userService.getUserInfo(userNo);
  }

  @Operation(summary = "[o]회원 탈퇴", description = "회원 탈퇴 API")
  @DeleteMapping()
  @UserRequired
  public void withdraw() {
    Long myUserNo = AuthUtil.getUserNoFromAuthentication();
    userService.withdraw(myUserNo);
    log.info(">>> UserController.withdrawl");
  }

  @Operation(summary = "[o]유저 팔로우하기", description = "유저 팔로우하기 API")
  @PostMapping("/follow/{userNo}")
  @UserRequired
  public UserFollowResponseDto followUser(@PathVariable("userNo") Long userNo) {
    log.info(">>> UserController.followUser");
    Long myUserNo = AuthUtil.getUserNoFromAuthentication();
    return UserFollowResponseDto.of(userService.followUser(myUserNo, userNo));
  }

  @Operation(summary = "[o]유저 언팔로우하기", description = "유저 언팔로우하기 API")
  @PostMapping("/unfollow/{userNo}")
  @UserRequired
  public void unfollowUser(@PathVariable("userNo") Long userNo) {
    log.info(">>> UserController.unfollowUser");
    Long myUserNo = AuthUtil.getUserNoFromAuthentication();
    userService.unfollowUser(myUserNo, userNo);
  }

}
