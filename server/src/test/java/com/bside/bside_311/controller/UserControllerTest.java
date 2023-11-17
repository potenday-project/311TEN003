package com.bside.bside_311.controller;

import com.bside.bside_311.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest extends ControllerTest{

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
}