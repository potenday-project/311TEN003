//package com.bside.bside_311.api.service;
//
//import com.bside.bside_311.dto.AddPostRequestDto;
//import com.bside.bside_311.entity.Post;
//import com.bside.bside_311.service.PostService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.SpyBean;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@Transactional
//public class PostServiceTest {
//
//  @SpyBean
//  private PostService postService;
//
//  @Test("should return post")
//  @DisplayName("postService.addPost")
//  void addPost() {
//    // given
//
//    // then
//    AddPostRequestDto addPostRequestDto = AddPostRequestDto.builder()
//                                                           .title("title")
//                                                           .content("content")
//                                                           .build();
//    postService.addPost(Post.of(addPostRequestDto), 1L, null, null);
//    // when
//
//    // then
//  }
//}
