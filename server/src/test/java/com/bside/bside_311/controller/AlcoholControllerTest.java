package com.bside.bside_311.controller;

import com.bside.bside_311.service.AlcoholService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlcoholController.class)
class AlcoholControllerTest extends ControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AlcoholService alcoholService;

  @Test
  void getAlcoholTypeList() throws Exception {
    //given
    //when
    mockMvc.perform(get("/alcohols/types"))
           .andExpect(status().isOk());

    //then
  }

  @Test
  void getAlcoholV2_success() throws Exception {
    // given
    // when
    // then
    String queryParameter = "?alcoholType=1&searchKeyword=소주";
    mockMvc.perform(get(String.format("/alcohols/v2%s", queryParameter)))
           .andExpect(status().isOk());
  }

  @Test
  void getAlcoholV2_fail() throws Exception {
    // given
    // when
    // then
    String queryParameter = "?alcoholType=asdf&searchKeyword=소주";
    mockMvc.perform(get(String.format("/alcohols/v2%s", queryParameter)))
           .andExpect(status().is4xxClientError());
  }
}