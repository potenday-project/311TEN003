package com.bside.bside_311.controller;

import com.bside.bside_311.dto.AddPostRequestDto;
import com.bside.bside_311.dto.AddPostResponseDto;
import com.bside.bside_311.entity.Post;
import com.bside.bside_311.service.PostService;
import com.bside.bside_311.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest extends ControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PostService postService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void addPost_success() throws Exception {
    //given
    AddPostRequestDto addPostRequestDto =
        AddPostRequestDto.builder().alcoholNo(1L).alcoholFeature("산뜻함. 달콤함.").postContent("게시글 내용")
                         .postType("BASIC").positionInfo("위치 정보").build();
    given(postService.addPost(any(), any(), any(), any())).willReturn(
        AddPostResponseDto.of(Post.builder().id(1L).build()));
    //when
    //then
    mockMvc.perform(post("/posts")
                        .header(HttpHeaders.AUTHORIZATION, JwtUtil.BEARER_PREFIX + userAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addPostRequestDto)))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.postNo").exists());
  }

  @Test
  void editPost_success() throws Exception {
    //given
    AddPostRequestDto addPostRequestDto =
        AddPostRequestDto.builder().alcoholNo(1L).alcoholFeature("산뜻함. 달콤함.").postContent("게시글 내용")
                         .postType("BASIC").positionInfo("위치 정보").build();
    //when
    //then
    mockMvc.perform(patch("/posts/1")
                        .header(HttpHeaders.AUTHORIZATION, JwtUtil.BEARER_PREFIX + userAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addPostRequestDto)))
           .andExpect(status().isOk());
  }

  @Test
  void deletePost_success() throws Exception {
    //given
    AddPostRequestDto addPostRequestDto =
        AddPostRequestDto.builder().alcoholNo(1L).alcoholFeature("산뜻함. 달콤함.").postContent("게시글 내용")
                         .postType("BASIC").positionInfo("위치 정보").build();
    //when
    //then
    mockMvc.perform(delete("/posts/1")
                        .header(HttpHeaders.AUTHORIZATION, JwtUtil.BEARER_PREFIX + userAccessToken)
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());
  }

  @Test
  void getPostsV2_success() throws Exception {
    //given
    AddPostRequestDto addPostRequestDto =
        AddPostRequestDto.builder().alcoholNo(1L).alcoholFeature("산뜻함. 달콤함.").postContent("게시글 내용")
                         .postType("BASIC").positionInfo("위치 정보").build();
    //when
    //then
    String queryParameter = "?page=1&size=10&sort=createdAt,desc&searchKeyword=test";
    String queryParameterSearchUserNos = "&searchUserNos=1,2,3";
    String queryParameterSearchAlcoholNos = "&searchAlcoholNos=1,2,3";
    mockMvc.perform(
               get(String.format("/posts/v2%s%s%s", queryParameter, queryParameterSearchUserNos,
                   queryParameterSearchAlcoholNos)))
           .andExpect(status().isOk());
  }

  @Test
  void getPostsV2_fail_due_to_parsing_error() throws Exception {
    //given
    AddPostRequestDto addPostRequestDto =
        AddPostRequestDto.builder().alcoholNo(1L).alcoholFeature("산뜻함. 달콤함.").postContent("게시글 내용")
                         .postType("BASIC").positionInfo("위치 정보").build();
    //when
    //then
    String queryParameter = "?page=1&size=10&sort=createdAt,desc&searchKeyword=test";
    String queryParameterSearchUserNos = "&searchUserNos=1,2,3_";
    String queryParameterSearchAlcoholNos = "&searchAlcoholNos=1,2,3_";
    mockMvc.perform(
               get(String.format("/posts/v2%s%s%s", queryParameter, queryParameterSearchUserNos,
                   queryParameterSearchAlcoholNos)))
           .andExpect(status().is4xxClientError());
  }

  @Test
  void getPostDetail_success() {
  }

  @Test
  void addComment_success() {
  }

  @Test
  void getPostComments_success() {
  }

  @Test
  void editComment_success() {
  }

  @Test
  void deleteComment() {
  }

  @Test
  void addQuote() {
  }

  @Test
  void deleteQuote() {
  }

  @Test
  void getQuotesByPost() {
  }

  @Test
  void likePost() {
  }

  @Test
  void likeCancelPost() {
  }
}