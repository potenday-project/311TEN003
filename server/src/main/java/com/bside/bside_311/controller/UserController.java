package com.bside.bside_311.controller;

import com.bside.bside_311.dto.ChangePasswordRequestDto;
import com.bside.bside_311.dto.LoginResponseDto;
import com.bside.bside_311.dto.MyInfoResponseDto;
import com.bside.bside_311.dto.UserLoginRequestDto;
import com.bside.bside_311.dto.UserSignupRequestDto;
import com.bside.bside_311.dto.UserUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "유저", description = "유저 API")
public class UserController {
  @Operation(summary = "유저 조회", description = "유저 조회 API")
  @GetMapping()
  public void getUser() {
    log.info(">>> UserController.getUser");
  }

  @Operation(summary = "유저 등록", description = "유저 등록 API")
  @PostMapping("/signup")
  public void signup(@RequestBody @Valid UserSignupRequestDto userSignupRequestDto) {

    log.info(">>> UserController.signup");
  }

  @Operation(summary = "유저 로그인", description = "유저 로그인 API")
  @PostMapping("/login")
  public LoginResponseDto login(@RequestBody @Valid UserLoginRequestDto userLoginRequestDto) {
    log.info(">>> UserController.login");
//    return userService.login(userLoginRequestDto);
    return null;
  }

  @Operation(summary = "성인 인증 API(추후)")
  @PostMapping("/self-auth")
  public void selfAuth(){
    log.info(">>> UserController.selfAuth");
  }

  @Operation(summary = "이메일 인증 API(추후)")
  @PostMapping("/email/authenticate")
  public void emailAuthenticate(){
    log.info(">>> UserController.emailAuthenticate");
  }

  @Operation(summary = "이메일 인증코드 확인API(추후)")
  @PostMapping("/email/authenticate/valid")
  public void emailAuthenticateValid(){
  log.info(">>> UserController.emailAuthenticateValid");
  }

  @Operation(summary = "임시 비밀번호 전달 API(추후)")
  @PostMapping("/password/temp/send")
  public void passwordTempSend(){
    log.info(">>> UserController.passwordTempSend");
  }

  @Operation(summary = "유저 정보 변경")
  @PatchMapping()
  public void updateUser(@RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto){
    log.info(">>> UserController.updateUser");
  }

  @Operation
  @PatchMapping("/pwd/change")
  public void changePassword(@RequestBody @Valid ChangePasswordRequestDto changePasswordRequestDto){
    log.info(">>> UserController.changePassword");
  }

  @Operation
  @GetMapping("/me")
  public MyInfoResponseDto getMyInfo(Authentication me){
    log.info(">>> UserController.getUserInfo");
    return null;
  }

  @Operation
  @GetMapping("/{userId}/summary")
  public void getUserInfo(){
    log.info(">>> UserController.getUserInfo");
  }

  @Operation
  @DeleteMapping()
  public void withdrawl(){
    log.info(">>> UserController.withdrawl");
  }

  @Operation(summary = "유저 팔로우하기", description = "유저 팔로우하기 API")
  @PostMapping("/follow/{user_no}")
  void followUser(@PathVariable("user_no") Long userNo){
    log.info(">>> UserController.followUser");
  }

  @Operation(summary = "유저 언팔로우하기", description = "유저 언팔로우하기 API")
  @PostMapping("/unfollow/{user_no}")
  void unfollowUser(@PathVariable("user_no") Long userNo){
    log.info(">>> UserController.followUser");
  }

  @Operation(summary = "프로필 사진 첨부", description = "프로필 사진 첨부 API")
  @PostMapping("/{user_no}/attach")
  public void userAttachPicture() {
    log.info(">>> PostController.userAttachPicture");
  }

  @Operation(summary = "프로필 사진 삭제", description = "프로필 사진 삭제 API")
  @PostMapping("/{user_no}/attach/{attach_no}")
  public void userDeletePicture() {
    log.info(">>> PostController.userDeletePicture");
  }


}
