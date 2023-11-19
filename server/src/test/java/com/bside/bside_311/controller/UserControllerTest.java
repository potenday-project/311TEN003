package com.bside.bside_311.controller;

import com.bside.bside_311.dto.MyInfoResponseDto;
import com.bside.bside_311.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest extends ControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

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
}