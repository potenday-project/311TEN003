package com.bside.bside_311.controller;

import com.bside.bside_311.dto.LoginResponseDto;
import com.bside.bside_311.dto.MyInfoResponseDto;
import com.bside.bside_311.dto.UserLoginRequestDto;
import com.bside.bside_311.dto.UserResponseDto;
import com.bside.bside_311.dto.UserSignupRequestDto;
import com.bside.bside_311.dto.UserUpdateRequestDto;
import com.bside.bside_311.entity.User;
import com.bside.bside_311.entity.UserFollow;
import com.bside.bside_311.service.UserService;
import com.bside.bside_311.util.JwtUtil;
import com.bside.bside_311.utils.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest extends ControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("회원가입 성공")
  void signupSuccess() throws Exception {
    String email = "newbie@example.com";
    String password = "password";
    String id = "newbie";
    String name = "Newbie";

    String json = String.format(
        """
            {
                "email": "%s",
                "password": "%s",
                "id": "%s",
                "nickname": "%s"
            }
            """,
        email, password, id, name
    );

    MvcResult mvcResult = mockMvc.perform(post("/user/signup")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(json))
                                 .andExpect(status().isCreated())
                                 .andReturn();
    String responseBody = mvcResult.getResponse().getContentAsString();
    System.out.println(responseBody);
  }

  @Test
  @DisplayName("회원가입 실패.")
  void signupFail_emptynickname() throws Exception {
    String email = "newbie@example.com";
    String password = "password";
    String id = "newbie";
    String name = null;

    String json = String.format(
        """
            {
                "email": "%s",
                "password": "%s",
                "id": "%s"
            }
            """,
        email, password, id, name
    );

    MvcResult mvcResult = mockMvc.perform(post("/user/signup")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(json))
                                 .andExpect(status().is4xxClientError())
                                 .andReturn();
    String responseBody = mvcResult.getResponse().getContentAsString();
    System.out.println(responseBody);
  }

  @Test
  @DisplayName("어드민 회원가입 성공")
  void signupAdminSuccess() throws Exception {

    MvcResult mvcResult = mockMvc.perform(post("/user/signup/admin-special")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(
                                                  UserUtils.makeSimpleUser())))
                                 .andExpect(status().isCreated())
                                 .andReturn();
    String responseBody = mvcResult.getResponse().getContentAsString();
    System.out.println(responseBody);
  }

  @Test
  @DisplayName("어드민 회원가입 실패_패스워드 없음.")
  void signupAdminFailDueToPassword() throws Exception {
    UserSignupRequestDto userSignupRequestDto = UserSignupRequestDto.builder()
                                                                    .email("test@example.com")
                                                                    .password(null)
                                                                    .id("testid")
                                                                    .nickname("nickname")
                                                                    .build();

    MvcResult mvcResult = mockMvc.perform(post("/user/signup/admin-special")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(
                                                  userSignupRequestDto)))
                                 .andExpect(status().is4xxClientError())
                                 .andReturn();
    String responseBody = mvcResult.getResponse().getContentAsString();
    System.out.println(responseBody);
  }

  @Test
  @DisplayName("로그인 성공")
  void loginSuccess() throws Exception {
    String id = "test1";
    String password = "password";
    UserLoginRequestDto userLoginRequestDto = UserLoginRequestDto.builder()
                                                                 .id(id)
                                                                 .password(password)
                                                                 .build();
    given(userService.login(userLoginRequestDto))
        .willReturn(LoginResponseDto.of("testsuccess"));
    mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                            userLoginRequestDto)))
           .andExpect(status().isOk());
  }

  @Test
  @DisplayName("로그인 실패. - 아이디 입력 부재.")
  void loginFail_id_not() throws Exception {
    String password = "password";
    UserLoginRequestDto userLoginRequestDto = UserLoginRequestDto.builder()
                                                                 .id(null)
                                                                 .password(password)
                                                                 .build();
    given(userService.login(userLoginRequestDto))
        .willReturn(LoginResponseDto.of("testsuccess"));
    mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                            userLoginRequestDto)))
           .andExpect(status().is4xxClientError());
  }

  @Test
  @DisplayName("유저 정보 수정 성공.")
  void userEditSuccess() throws Exception {
    UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto("test", "안녕하세요~~~");

    mockMvc.perform(patch("/user")
                        .header(HttpHeaders.AUTHORIZATION, JwtUtil.BEARER_PREFIX + userAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateRequestDto
                        )))
           .andExpect(status().isOk());
  }

  @Test
  @DisplayName("유저 비밀번호 변경 성공.")
  void userChangePassword() throws Exception {
    UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto("test", "안녕하세요~~~");

    mockMvc.perform(patch("/user/pwd/change")
                        .header(HttpHeaders.AUTHORIZATION, JwtUtil.BEARER_PREFIX + userAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateRequestDto
                        )))
           .andExpect(status().isOk());
  }

  @Test
  @DisplayName("GET /me success")
  void me() throws Exception {
    given(userService.getMyInfo(normalUser.getId()))
        .willReturn(MyInfoResponseDto.of(normalUser, null, 0L,
            0L));

    mockMvc.perform(get("/user/me")
                        .header("Authorization", "Bearer " + userAccessToken))
           .andExpect(status().isOk())
           .andExpect(content().string(containsString("userNo")));
  }

  @Test
  @DisplayName("회원 탈퇴. 성공.")
  void user_delete_success() throws Exception {

    mockMvc.perform(delete("/user")
                        .header("Authorization", "Bearer " + userAccessToken))
           .andExpect(status().isOk());
  }

  @Test
  @DisplayName("유저 팔로우. 성공.")
  void user_follow_success() throws Exception {
    given(userService.followUser(normalUser.getId(), 2L))
        .willReturn(UserFollow.builder().following(normalUser).followed(User.of(2L))
                              .build());

    mockMvc.perform(post(String.format("/user/follow/%d", 2))
                        .header("Authorization", "Bearer " + userAccessToken))
           .andExpect(status().isOk())
           .andExpect(content().string(containsString("userFollowNo")));
  }

  @Test
  @DisplayName("유저 언팔로우. 성공.")
  void user_unfollow_success() throws Exception {
    mockMvc.perform(post(String.format("/user/unfollow/%d", 2))
                        .header("Authorization", "Bearer " + userAccessToken))
           .andExpect(status().isOk());
  }

  @Test
  @DisplayName("내가 팔로잉하는 유저 조회. 성공.")
  void getMyFollowingUsers_success() throws Exception {
    //given
    Page<UserResponseDto> pagedResponse =
        new PageImpl<>(List.of(UserResponseDto.of(normalUser, null)));
    given(userService.getMyFollowingUsers(eq(normalUser.getId()),
        ArgumentMatchers.any(Pageable.class)))
        .willReturn(pagedResponse);
    mockMvc.perform(get("/user//my-following-users")
                        .header("Authorization", "Bearer " + userAccessToken))
           .andExpect(status().isOk())
           .andExpect(content().string(containsString("userNo")));
  }

  @Test
  @DisplayName("나를 팔로잉하는 유저 조회. 성공.")
  void getUsersOfFollowingMePage_success() throws Exception {
    //given
    Page<UserResponseDto> pagedResponse =
        new PageImpl<>(List.of(UserResponseDto.of(normalUser, null)));
    given(userService.getUsersOfFollowingMe(eq(normalUser.getId()),
        ArgumentMatchers.any(Pageable.class)))
        .willReturn(pagedResponse);
    mockMvc.perform(get("/user/users-of-following-me")
                        .header("Authorization", "Bearer " + userAccessToken))
           .andExpect(status().isOk())
           .andExpect(content().string(containsString("userNo")));
  }
}