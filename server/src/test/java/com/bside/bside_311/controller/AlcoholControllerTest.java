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
}